package com.fushun.framework.redisson.util;

import com.fushun.framework.exception.DynamicBaseException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RedissonUtil {

    private static RedissonClient redisClientStatic;

    public RedissonUtil(RedissonClient redissonClient){
        RedissonUtil.redisClientStatic=redissonClient;
    }

    /**
     *
     * @param redisKey
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T executeLock(String redisKey, Supplier<T> function){
       return RedissonUtil.execute(redisKey,function,rLock -> {
            rLock.lock();
            return true;
        });
    }

    /**
     * 分布式锁执行 统一执行
     * @param redisKey
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T executeTryLock(String redisKey,int lockTimeOut, Supplier<T> function){
        return RedissonUtil.executeTryLock(redisKey,lockTimeOut,lockTimeOut,function);
    }

    /**
     *
     * @param redisKey
     * @param waitTime
     * @param leaseTime
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T executeTryLock(String redisKey,long waitTime, long leaseTime, Supplier<T> function){
        return RedissonUtil.execute(redisKey,function, lock-> {
            try {
                return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new DynamicBaseException(e,"LOCK_ERROR","请求超时，请重试！");
            }
        });

    }

    private static <T> T execute(String redisKey, Supplier<T> function, Function<RLock,Boolean> lockFun){
        RLock lock = redisClientStatic.getFairLock(redisKey);
        double startTime=0L;
        double getLockTime=0L;
        try {
//            lock.lock(constant.getLockTimeOut(), TimeUnit.SECONDS);
            // 尝试加锁，最多等待10秒，上锁以后10秒自动解锁
            startTime=System.currentTimeMillis();
            boolean res = lockFun.apply(lock);
            getLockTime=System.currentTimeMillis();
            if(!res){
                throw new DynamicBaseException("USER_MORE_LOCK_FAIL_ERROR","当前操作用户过多，请重试！");
            }
            return function.get();
        } finally {
            double businessTime=System.currentTimeMillis();
            try {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }catch (Exception e){
                log.info(e.getMessage(),e);
            }

            double releaseLockTime=System.currentTimeMillis();
            log.info("分布式锁：{}。获得锁耗时：{},业务执行时间：{}，释放锁耗时:{},总耗时：{}",
                    redisKey,
                    (getLockTime-startTime)/1000,
                    (businessTime-getLockTime)/1000,
                    (releaseLockTime-businessTime)/1000,
                    (releaseLockTime-startTime)/1000);
        }
    }

}
