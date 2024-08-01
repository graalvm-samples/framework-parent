package com.fushun.framework.redis.utils;

import com.fushun.framework.redis.config.IntegerRedisTemplate;
import com.fushun.framework.redis.config.LongRedisTemplate;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
public class RedisUtil{

    private Logger logger= LoggerFactory.getLogger(this.getClass());

//    @Autowired
    @Setter
    public RedisTemplate redisTemplate;

    @Setter
    public StringRedisTemplate stringRedisTemplate;

    @Setter
    public IntegerRedisTemplate integerRedisTemplate;

    @Setter
    public LongRedisTemplate longRedisTemplate;

    public void RedisUtil(){

    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    public  <T> boolean setCacheObjectIfAbsent(final String key,  final T value,
                            final Integer timeout
            , final TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout,
                timeUnit);
    }
    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    public long getExpire(String key){
       return redisTemplate.getExpire(key);
    }



    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        try {
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            return operation.get(key);
        }catch (Exception e){
            logger.error("getCacheObject",e);
        }
        return null;
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public Boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        }catch (Exception e){
            logger.error("getCacheList",e);
        }
        return null;
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @SafeVarargs
    public final<V> Long setCacheSet(String key, V... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error("set key:{} fair messages:{}", key, e);
            return 0L;
        }
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        try {
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            logger.error("getCacheSet",e);
        }
        return null;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public <T> Boolean setHasKey(String key, T value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
            return false;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public <T> Long setRemove(String key, T... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            logger.error("set key:{} fair messages:{}", key, e);
            return 0L;
        }
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        try {
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            logger.error("getCacheMap",e);
        }
        return null;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        try {
            HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
            return opsForHash.get(key, hKey);
        }catch (Exception e){
            logger.error("getCacheMapValue",e);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        try {
            return redisTemplate.opsForHash().multiGet(key, hKeys);
        }catch (Exception e){
            logger.error("getMultiCacheMapValue",e);
        }
        return null;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key
     * @param hkey
     */
    public void delCacheMapValue(final String key, final String hkey)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 原子加value
     * @param key
     * @param value
     */
    public Long increment(final String key,long value){
        return redisTemplate.opsForValue().increment(key, value);
    }

    public String getString(final String key){
        try {
            return stringRedisTemplate.opsForValue().get(key);
        }catch (Exception e){
            logger.error("getString",e);
        }
        return null;
    }
    public void setString(final String key,String value){
         stringRedisTemplate.opsForValue().set(key,value);
    }
    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<String> getStringCacheSet(final String key)
    {
        try {
            return stringRedisTemplate.opsForSet().members(key);
        }catch (Exception e){
            logger.error("getStringCacheSet",e);
        }
        return null;
    }
    public void setString (final String key, final String value,
                           final Integer timeout, final TimeUnit timeUnit){
        stringRedisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }
    public boolean setStringIfAbsent(final String key,  final String value,
                                            final Integer timeout
            , final TimeUnit timeUnit){
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value,
                timeout, timeUnit);
    }

    public Integer getIngeter(final String key){
        try {
            return integerRedisTemplate.opsForValue().get(key);
        }catch (Exception e){
            logger.error("getIngeter",e);
        }
        return null;
    }
    public void setIngeter(final String key,Integer value){
        integerRedisTemplate.opsForValue().set(key, value);
    }
    public void setIngeter (final String key, final Integer value,
                           final Integer timeout, final TimeUnit timeUnit){
        integerRedisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<Integer> getIngeterCacheSet(final String key)
    {
        try {
            return integerRedisTemplate.opsForSet().members(key);
        }catch (Exception e){
            logger.error("getIngeterCacheSet",e);
        }
        return null;
    }

    public Long getLong(final String key){
        Object valueObj = longRedisTemplate.opsForValue().get(key);
        Long value = null;
        if (valueObj!=null && valueObj instanceof Integer) {
            value = ((Integer) valueObj).longValue();
        } else if (valueObj!=null && valueObj instanceof Long) {
            value = (Long) valueObj;
        }
        return value;
    }
    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<Long> getLongCacheSet(final String key)
    {
        return longRedisTemplate.opsForSet().members(key);
    }
    public void setLong(final String key,Long value){
        longRedisTemplate.opsForValue().set(key, value);
    }
    public void setLong (final String key, final Long value,
                            final Integer timeout, final TimeUnit timeUnit){
        longRedisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    public <T,K> T execute(RedisScript<T> redisScript,List<String> list,
                              Object... value){
        return longRedisTemplate.execute(redisScript,
                list, value);
    }

}
