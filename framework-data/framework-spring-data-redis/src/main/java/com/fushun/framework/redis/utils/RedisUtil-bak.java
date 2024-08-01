//package com.fushun.framework.redis.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//import org.springframework.scripting.support.StaticScriptSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisUtil<K, V> {
//
//    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
//
//    private RedisTemplate<K, V> redisTemplate;
//
//    @Autowired
//    public RedisUtil(RedisTemplate<K, V> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public RedisUtil() {
//    }
//
//    private static final DefaultRedisScript<Long> INCR_BY_LUA_REDIS_SCRIPT = new DefaultRedisScript<>();
//
//    private static final DefaultRedisScript<String> INCR_BY_FLOAT_LUA_REDIS_SCRIPT = new DefaultRedisScript<>();
//
//    static {
//        String incrByLua = "local exists = redis.call('exists',KEYS[1])\n" + "if exists == 1 then\n"
//                + "    local ttl = redis.call('ttl',KEYS[1])\n" + "    if ttl == -1 then\n"
//                + "        redis.call('expire',KEYS[1],ARGV[2])\n" + "    end\n"
//                + "    return redis.call('INCRBY',KEYS[1],ARGV[1])\n" + "else\n"
//                + "    local value = redis.call('INCRBY',KEYS[1],ARGV[1])\n" + "    redis.call('expire',KEYS[1],ARGV[2])\n"
//                + "    return value\n" + "end";
//        INCR_BY_LUA_REDIS_SCRIPT.setResultType(Long.class);
//        INCR_BY_LUA_REDIS_SCRIPT.setScriptSource(new StaticScriptSource(incrByLua));
//
//        String incrByFloatLua = "local exists = redis.call('exists',KEYS[1])\n" + "if exists == 1 then\n"
//                + "    local ttl = redis.call('ttl',KEYS[1])\n" + "    if ttl == -1 then\n"
//                + "        redis.call('expire',KEYS[1],ARGV[2])\n" + "    end\n"
//                + "    return redis.call('INCRBYFLOAT',KEYS[1],ARGV[1])\n" + "else\n"
//                + "    local value = redis.call('INCRBYFLOAT',KEYS[1],ARGV[1])\n"
//                + "    redis.call('expire',KEYS[1],ARGV[2])\n" + "    return value\n" + "end";
//        INCR_BY_FLOAT_LUA_REDIS_SCRIPT.setResultType(String.class);
//        INCR_BY_FLOAT_LUA_REDIS_SCRIPT.setScriptSource(new StaticScriptSource(incrByFloatLua));
//    }
//
//    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//
//    // =============================common============================
//
//    /**
//     * 指定缓存失效时间
//     *
//     * @param key  键
//     * @param time 时间(秒)
//     */
//    public Boolean expire(K key, long time) {
//        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 获得缓存的基本对象列表
//     *
//     * @param pattern 字符串前缀
//     * @return 对象列表
//     */
//    public Collection<K> keys(K pattern) {
//        return redisTemplate.keys(pattern);
//    }
//
//    /**
//     * 根据key 获取过期时间
//     *
//     * @param key 键 不能为null
//     * @return 时间(秒) 返回代表为永久有效
//     */
//    public Long getExpire(K key) {
//        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key 键
//     * @return true 存在 false不存在
//     */
//    public Boolean hasKey(K key) {
//        return redisTemplate.hasKey(key);
//    }
//
//    /**
//     * 删除缓存
//     *
//     * @param key 可以传一个值 或多个
//     */
//    @SuppressWarnings("unchecked")
//    public void del(K... key) {
//        if (key != null && key.length > 0) {
//            if (key.length == 1) {
//                redisTemplate.delete(key[0]);
//            } else {
//                redisTemplate.delete(CollectionUtils.arrayToList(key));
//            }
//        }
//    }
//
//    // ============================String=============================
//
//    /**
//     * 普通缓存获取
//     *
//     * @param key 键
//     * @return 值
//     */
//    public V get(K key) {
//        return key == null ? null : redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 普通缓存放入
//     *
//     * @param key   键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public boolean set(K key, V value) {
//        try {
//            redisTemplate.opsForValue().set(key, value);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒) time要大于 如果time小于等于 将设置无限期
//     * @return true成功 false 失败
//     */
//    public boolean set(K key, V value, long time) {
//        try {
//            if (time > 0) {
//                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//            } else {
//                set(key, value);
//            }
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒) time要大于 如果time小于等于 将设置无限期
//     * @return true成功 false 失败
//     */
//    public boolean set(K key, V value, long time, TimeUnit timeUnit) {
//        try {
//            if (time > 0) {
//                redisTemplate.opsForValue().set(key, value, time, timeUnit);
//            } else {
//                set(key, value);
//            }
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 递增
//     *
//     * @param key   键
//     * @param delta 要增加几(大于)
//     * @return
//     */
//    public long incr(K key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递增因子必须大于");
//        }
//        long index = redisTemplate.opsForValue().increment(key, delta);
//        return index;
//    }
//
//    /**
//     * 整形递增
//     *
//     * @param key   键
//     * @param delta 要增加几(大于0)
//     * @return
//     */
//    public Long longIncr(K key, long delta, long time) {
//        if (delta < 0) {
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        long index = redisTemplate.opsForValue().increment(key, delta);
//        expire(key, time);
//        return index;
//    }
//
//    /**
//     * 递减
//     *
//     * @param key   键
//     * @param delta 要减少几(小于)
//     * @return
//     */
//    public Long decr(K key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, -delta);
//    }
//
//    // ================================Map=================================
//
//    /**
//     * HashGet
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return 值
//     */
//    public Object hget(K key, String item) {
//        return redisTemplate.opsForHash().get(key, item);
//    }
//
//    /**
//     * 获取hashKey对应的所有键值
//     *
//     * @param key 键
//     * @return 对应的多个键值
//     */
//    public Map<Object, Object> hmget(K key) {
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * HashSet
//     *
//     * @param key 键
//     * @param map 对应多个键值
//     * @return true 成功 false 失败
//     */
//    public boolean hmset(K key, Map<String, Object> map) {
//        try {
//            redisTemplate.opsForHash().putAll(key, map);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return false;
//        }
//    }
//
//    public <HK, HV> Boolean setMap(K key, Map<HK, HV> map, Long expireTime) {
//        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
//        operations.putAll(key, map);
//
//        if (expireTime != null) {
//            return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
//        }
//        return false;
//    }
//
//    public <HK, HV> Map<HK, HV> getMap(final K key) {
//        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
//        return operations.entries(key);
//    }
//
//    /**
//     * HashSet 并设置时间
//     *
//     * @param key  键
//     * @param map  对应多个键值
//     * @param time 时间(秒)
//     * @return true成功 false失败
//     */
//    public boolean hmset(K key, Map<String, Object> map, long time) {
//        try {
//            redisTemplate.opsForHash().putAll(key, map);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     *
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @return true 成功 false失败
//     */
//    public boolean hset(K key, String item, V value) {
//        try {
//            redisTemplate.opsForHash().put(key, item, value);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     *
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
//     * @return true 成功 false失败
//     */
//    public boolean hset(K key, String item, V value, long time) {
//        try {
//            redisTemplate.opsForHash().put(key, item, value);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 删除hash表中的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 可以使多个 不能为null
//     */
//    public void hdel(K key, Object... item) {
//        redisTemplate.opsForHash().delete(key, item);
//    }
//
//    /**
//     * 判断hash表中是否有该项的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return true 存在 false不存在
//     */
//    public boolean hHasKey(K key, String item) {
//        return redisTemplate.opsForHash().hasKey(key, item);
//    }
//
//    /**
//     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//     *
//     * @param key  键
//     * @param item 项
//     * @param by   要增加几(大于)
//     * @return
//     */
//    public double hincr(K key, String item, double by) {
//        return redisTemplate.opsForHash().increment(key, item, by);
//    }
//
//    /**
//     * hash递减
//     *
//     * @param key  键
//     * @param item 项
//     * @param by   要减少记(小于)
//     * @return
//     */
//    public double hdecr(K key, String item, double by) {
//        return redisTemplate.opsForHash().increment(key, item, -by);
//    }
//
//    // ============================set=============================
//
//    /**
//     * 根据key获取Set中的所有值
//     *
//     * @param key 键
//     * @return
//     */
//    public Set<V> sGet(K key) {
//        try {
//            return redisTemplate.opsForSet().members(key);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return null;
//        }
//    }
//
//    /**
//     * 根据value从一个set中查询,是否存在
//     *
//     * @param key   键
//     * @param value 值
//     * @return true 存在 false不存在
//     */
//    public Boolean sHasKey(K key, V value) {
//        try {
//            return redisTemplate.opsForSet().isMember(key, value);
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 将数据放入set缓存
//     *
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    @SafeVarargs
//    public final Long sSet(K key, V... values) {
//        try {
//            return redisTemplate.opsForSet().add(key, values);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return 0L;
//        }
//    }
//
//    /**
//     * 将set数据放入缓存
//     *
//     * @param key    键
//     * @param time   时间(秒)
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    @SafeVarargs
//    public final Long sSetAndTime(K key, long time, V... values) {
//        try {
//            Long count = redisTemplate.opsForSet().add(key, values);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return count;
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return 0L;
//        }
//    }
//
//    /**
//     * 获取set缓存的长度
//     *
//     * @param key 键
//     * @return
//     */
//    public Long sGetSetSize(K key) {
//        try {
//            return redisTemplate.opsForSet().size(key);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return 0L;
//        }
//    }
//
//    /**
//     * 移除值为value的
//     *
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 移除的个数
//     */
//    public Long setRemove(K key, Object... values) {
//        try {
//            return redisTemplate.opsForSet().remove(key, values);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return 0L;
//        }
//    }
//
//    // ===============================list=================================
//
//    /**
//     * 获取list缓存的内容
//     *
//     * @param key   键
//     * @param start 开始
//     * @param end   结束 到 -代表所有值
//     * @return
//     */
//    public List<V> lGet(K key, long start, long end) {
//        try {
//            return redisTemplate.opsForList().range(key, start, end);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取list缓存的长度
//     *
//     * @param key 键
//     * @return
//     */
//    public Long lGetListSize(K key) {
//        try {
//            return redisTemplate.opsForList().size(key);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return 0L;
//        }
//    }
//
//    /**
//     * 通过索引 获取list中的值
//     *
//     * @param key   键
//     * @param index 索引 index>=时， 表头， 第二个元素，依次类推；index<时，-，表尾，-倒数第二个元素，依次类推
//     * @return
//     */
//    public Object lGetIndex(K key, long index) {
//        try {
//            return redisTemplate.opsForList().index(key, index);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return null;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @return
//     */
//    public boolean lSet(K key, V value) {
//        try {
//            redisTemplate.opsForList().leftPush(key, value);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//
//    /**
//     * <b>方法名：</b> rightPop<br>
//     * <b>功能说明：</b>从右边取出一个值并弹出<br>
//     *
//     * @param key
//     * @author <font color='blue'>perry</font>
//     * @date 2019/8/22 19:29
//     */
//    public Object rightPop(K key) {
//        try {
//            return redisTemplate.opsForList().rightPop(key);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//            return null;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @return
//     */
//    public boolean rSet(K key, V value) {
//        try {
//            redisTemplate.opsForList().rightPush(key, value);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     * @return
//     */
//    public boolean rSet(K key, V value, long time) {
//        try {
//            redisTemplate.opsForList().rightPush(key, value);
//            if (time > 0) {
//                expire(key, time);
//                return true;
//            } else {
//                return false;
//            }
//
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     * @return
//     */
//    public boolean lSet(K key, long time, V... value) {
//        try {
//            redisTemplate.opsForList().rightPushAll(key, value);
//            if (time > 0) {
//                expire(key, time);
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//
//    public boolean setList(K key, V collection, Long expireTime) {
//        boolean result = false;
//        try {
//            redisTemplate.opsForList().leftPush(key, collection);
//            if (expireTime != null) {
//                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
//            }
//            result = true;
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//        }
//        return result;
//    }
//
//    public List<?> getList(K key) {
//        List<?> collection = null;
//        try {
//            collection = redisTemplate.opsForList().range(key, 0, -1);
//        } catch (Exception e) {
//            logger.error("set key:{} fair messages:{}", key, e);
//        }
//        return collection;
//    }
//
//    /**
//     * 根据索引修改list中的某条数据
//     *
//     * @param key   键
//     * @param index 索引
//     * @param value 值
//     * @return
//     */
//    public boolean lUpdateIndex(K key, long index, V value) {
//        try {
//            redisTemplate.opsForList().set(key, index, value);
//            return true;
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return false;
//        }
//    }
//
//    /**
//     * 移除N个值为value
//     *
//     * @param key   键
//     * @param count 移除多少个
//     * @param value 值
//     * @return 移除的个数
//     */
//    public Long lRemove(K key, long count, V value) {
//        try {
//            return redisTemplate.opsForList().remove(key, count, value);
//        } catch (Exception e) {
//            logger.error("set key:{} value:{} fair messages:{}", key, value, e);
//            return 0L;
//        }
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key
//     * @return Boolean
//     */
//    public Boolean exists(K key) {
//        return redisTemplate.hasKey(key);
//    }
//}
