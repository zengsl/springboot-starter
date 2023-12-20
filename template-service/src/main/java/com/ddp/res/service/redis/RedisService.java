package com.ddp.res.service.redis;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ddp.res.common.utils.JacksonUtil;
import com.google.common.base.Throwables;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * spring redis 工具类
 * <p>
 * keySerializer做了环境隔离
 * valueSerializer使用了jackson2，如果是序列化对象则会保存类信息用于反序列化。如果不存在多态的情况，没必要序列化类信息，增加存储空间且不安全，所以可以转成String存储，如：redisService.setCacheObject(cacheKey, JacksonUtil.obj2String(new SysConfigVO(config.getConfigValue(), config.getViewScope())));
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
@Slf4j
@Getter
public class RedisService {

    public final RedisTemplate redisTemplate;

    private final RedisSerializer keySerializer;

    private final RedisSerializer valueSerializer;

    public RedisService(RedisTemplate redisTemplate, @Qualifier("keySerializer") RedisSerializer keySerializer, @Qualifier("valueSerializer") RedisSerializer valueSerializer) {
        Assert.notNull(redisTemplate, "redisTemplate not be null");
        Assert.notNull(keySerializer, "keySerializer not be null");
        Assert.notNull(valueSerializer, "valueSerializer not be null");
        this.redisTemplate = redisTemplate;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * <p>
     * valueSerializer使用了jackson2，如果是序列化对象则会保存类信息用于反序列化。如果不存在多态的情况，没必要序列化类信息，增加存储空间且不安全，所以可以转成String存储，如：redisService.setCacheObject(cacheKey, JacksonUtil.obj2String(new SysConfigVO(config.getConfigValue(), config.getViewScope())));
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * <p>
     * valueSerializer使用了jackson2，如果是序列化对象则会保存类信息用于反序列化。如果不存在多态的情况，没必要序列化类信息，增加存储空间且不安全，所以可以转成String存储，如：redisService.setCacheObject(cacheKey, JacksonUtil.obj2String(new SysConfigVO(config.getConfigValue(), config.getViewScope())));
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * <p>
     * valueSerializer使用了jackson2，如果是序列化对象则会保存类信息用于反序列化。如果不存在多态的情况，没必要序列化类信息，增加存储空间且不安全，所以可以转成String存储，如：redisService.setCacheObject(cacheKey, JacksonUtil.obj2String(new SysConfigVO(config.getConfigValue(), config.getViewScope())));
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     */
    public <T> void setCacheObject(final String key, final T value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    /**
     * pipeline 设置 key-value 并设置过期时间（集群模式下需要处理pipelined问题）
     *
     * @param keyValues key value集合
     * @param seconds   过期时间
     */
    public void pipelineSetEx(Map<String, String> keyValues, Long seconds) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    connection.setEx(keySerializer.serialize(entry.getKey()), seconds,
                        valueSerializer.serialize(entry.getValue()));
                }
                return null;
            });
        } catch (Exception e) {
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param keys 多个缓存键值
     * @return 缓存键值对应的数据，并将结果包装为map
     */
    public <T> Map<String, T> getCacheObject(List<String> keys) {
        Map<String, T> result = new HashMap<>(keys.size());
        try {
            List<T> value = redisTemplate.opsForValue().multiGet(keys);
            if (CollUtil.isNotEmpty(value)) {
                for (int i = 0; i < keys.size(); i++) {
                    result.put(keys.get(i), value.get(i));
                }
            }
        } catch (Exception e) {
            log.error("RedisService#getCacheObject fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 通过key模式串删除缓存
     *
     * @param keyPattern key模式串
     * @return Long
     */
    public boolean deleteWitKeyPattern(final String keyPattern) {
        return deleteWithKeys(redisTemplate.keys(keyPattern));
    }

    /**
     * 通过key模式串删除缓存
     *
     * @param collection key模式串
     * @return Long
     */
    public boolean deleteWitKeyPattern(final Collection collection) {
        Set keys = new HashSet<>();
        collection.forEach(k -> {
            Set tmpKeys = redisTemplate.keys(k);
            if (CollectionUtils.isNotEmpty(tmpKeys)) {
                keys.addAll(tmpKeys);
            }
        });

        return deleteWithKeys(keys);
    }

    private boolean deleteWithKeys(Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        return count != null && count > 0;
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection) {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
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
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String... hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }


    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将key存放的值增加val
     *
     * @param key Redis键
     * @param val 需增加的数值
     * @return 返回计算的结果
     */
    public Long increment(String key, long val) {
        return redisTemplate.opsForValue().increment(key, val);
    }


    /**
     * 从缓存获取数据，如果没有就从dataProviderFn获取
     *
     * @param key            缓存key
     * @param dataProviderFn 缓存没有数据时，获取数据
     * @param cacheDuration  缓存时间（缓存没有时设置）
     * @return T
     */
    public <T> T getDataFormCacheOrSourceIfNullAndCacheIfNeed(final String key, Function<String, T> dataProviderFn, Duration cacheDuration) {
        T cacheData = getCacheObject(key);
        if (cacheData == null) {
            cacheData = dataProviderFn.apply(key);
            if (cacheData == null) {
                return null;
            }
            if (cacheDuration == null) {
                setCacheObject(key, cacheData);
            } else {
                setCacheObject(key, cacheData, cacheDuration);
            }
        }
        return cacheData;
    }

    /**
     * 从缓存获取数据，如果没有就从dataProviderFn获
     *
     * @param key            缓存key
     * @param dataProviderFn 缓存没有数据时，获取数据
     * @param cacheDuration  缓存时间（缓存没有时设置）
     * @return T
     */
    public <T> T getDataFormCacheOrSourceIfNullAndCacheList(final String key, Function<String, T> dataProviderFn, Duration cacheDuration, Function<String, T> deSerializeFn) {
        T cacheData;
        String cacheStr = getCacheObject(key);
        if (cacheStr == null) {
            cacheData = dataProviderFn.apply(key);
            if (cacheData == null) {
                return null;
            }
            cacheStr = JacksonUtil.obj2String(cacheData);
            if (cacheDuration == null) {
                setCacheObject(key, cacheStr);
            } else {
                setCacheObject(key, cacheStr, cacheDuration);
            }
            return cacheData;
        } else {
            return deSerializeFn.apply(cacheStr);
        }
    }


    /**
     * 从缓存获取数据，如果没有就从dataProviderFn获
     *
     * @param key            缓存key
     * @param dataProviderFn 缓存没有数据时，获取数据
     * @param cacheDuration  缓存时间（缓存没有时设置）
     * @return T
     */
    public <T> T getDataFormCacheOrSourceIfNullAndCacheObject(final String key, Function<String, T> dataProviderFn, Duration cacheDuration, Class<T> clazz) {
        return getDataFormCacheOrSourceIfNullAndCacheList(key, dataProviderFn, cacheDuration, (cacheStr) -> JacksonUtil.string2Obj(cacheStr, clazz));
    }


    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流 key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript lua脚本
     * @param keys        key集合
     * @param args        lua脚本参数
     * @return boolean 成功与否
     */
    public Boolean execLimitLua(RedisScript<Long> redisScript, List<String> keys, String... args) {

        try {
            Long execute = (Long) redisTemplate.execute(redisScript, new StringRedisSerializer(), valueSerializer, keys, args);
            if (Objects.isNull(execute)) {
                return false;
            }
            return Integer.valueOf(1).equals(execute.intValue());
        } catch (Exception e) {
            log.error("redis execLimitLua fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return false;
    }

    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流 key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript lua脚本
     * @param keys        key集合
     * @param args        lua脚本参数
     * @return boolean 成功与否
     */
    public <T> T execLimitLua2(RedisScript<T> redisScript, List<String> keys, String... args) {
        return (T) redisTemplate.execute(redisScript, new StringRedisSerializer(), valueSerializer, keys, args);
    }


    /**
     * 单key 值为value时，删除key
     *
     * @param key   缓存key
     * @param value 缓存key对应的值
     * @return Boolean
     */
    public Boolean deleteKeyIfValueEquals(String key, String value) {
        return deleteKeyIfValueEquals(key, value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 单key
     *
     * @param key   缓存key
     * @param value 缓存key对应的值
     * @return Boolean
     */
    public Boolean deleteKeyIfValueEquals(String key, byte[] value) {
        byte[] scriptBytes = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end".getBytes(StandardCharsets.UTF_8);
        RedisCallback<Boolean> callback = (RedisConnection connection) -> connection.eval(scriptBytes, ReturnType.BOOLEAN, 1, getKeySerializer().serialize(key), value);
        return (Boolean) redisTemplate.execute(callback);
    }


    public <T> T execute(RedisCallback<T> action) {
        return (T) redisTemplate.execute(action);
    }

    /**
     * zet增加操作<br/>
     * key和value存在新增失败，返回false；不存在新增成功，返回true。
     *
     * @param zKey          缓存key
     * @param propertyName  属性值 (member)
     * @param propertyValue 具体分数 value
     * @return 是否成功
     */
    public <V> Boolean zsAdd(String zKey, V propertyName, Double propertyValue) {
        return redisTemplate.opsForZSet().add(zKey, propertyName, propertyValue);
    }

    /**
     * zet增加操作<br/>
     *
     * @param zKey   缓存key
     * @param tuples 键值对
     * @return 是否成功
     */
    public <V> Long zsAddIfAbsent(String zKey, Set<ZSetOperations.TypedTuple<V>> tuples) {
        return redisTemplate.opsForZSet().addIfAbsent(zKey, tuples);
    }

    /**
     * zet增加操作<br/>
     *
     * @param zKey   缓存key
     * @param tuples 键值对
     * @return 是否成功
     */
    public <V> Long zsAdd(String zKey, Set<ZSetOperations.TypedTuple<V>> tuples) {
        return redisTemplate.opsForZSet().add(zKey, tuples);
    }

    /**
     * 根据key和value移除指定元素
     *
     * @param zKey         缓存key
     * @param propertyName 属性值 (member)
     * @return 异常数量
     */
    public Long zsRemove(String zKey, Object... propertyName) {
        return redisTemplate.opsForZSet().remove(zKey, propertyName);
    }


    /**
     * 获取zset集合数量
     *
     * @param key
     * @return
     */
    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }


    /**
     * zset给某个key某个属性增值操作
     *
     * @param zKey         缓存key
     * @param propertyName 属性值 (member)
     * @param delta        增加值
     * @return 增加后值
     */
    public <V> Double zsIncr(String zKey, V propertyName, Integer delta) {
        return redisTemplate.opsForZSet().incrementScore(zKey, propertyName, delta);
    }


    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     * <p>
     * 返回有序的集合，score小的在前面
     *
     * @param key   缓存key
     * @param start 开始位置
     * @param end   结束位置
     * @return
     */
    public Set<String> range(String key, int start, int end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     * <p>
     * 返回有序的集合，score小的在前面
     *
     * @param key 缓存key
     * @return 值
     */
    public Set<String> range(String key) {
        return range(key, 0, -1);
    }

    /**
     * zSet逆向排序
     *
     * @param key 缓存key
     * @return 值
     */
    public Set<Object> zReverseRange(String key) {
        return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
    }

    /**
     * zSet逆向排序
     *
     * @param key 缓存key
     * @return 值
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zReverseRangeWithScore(String key) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
    }

    /**
     * zSet 获取数据
     *
     * @param key 缓存key
     * @return 值
     */
    public Set<ZSetOperations.TypedTuple> zRangeWithScores(String key) {
        return zRangeWithScores(key, 0, -1);
    }

    /**
     * zSet获取数据
     *
     * @param key   缓存key
     * @param start 开始位置
     * @param end   结束位置
     * @return 值
     */
    public Set<ZSetOperations.TypedTuple> zRangeWithScores(String key, int start, int end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * zscore 返回属性值
     *
     * @param key   key值
     * @param value 属性值
     * @return 值
     */
    public Double zScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Long removeRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    public void convertAndSend(String topic, Object value) {
        redisTemplate.convertAndSend(topic, value);
    }


    /**
     * 如果不存在则设置 并返回 true 如果存在则返回 false
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  超时时间
     * @param timeUnit 超时时间单位
     * @return set成功或失败
     */
    public <T> boolean setObjectIfAbsent(final String key, final T value, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit));
    }
}
