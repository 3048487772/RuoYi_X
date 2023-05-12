package com.ruoyi.common.core.redis;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    @Autowired
    public RedisTemplate redisTemplate;


    @Value("${spring.redis.prefix}")
    private String keyPrefix="";

    private String addKeyPrefix(String key, boolean needPrefix) {
        if (needPrefix) {
            if (StrUtil.isNotEmpty(keyPrefix) && !key.startsWith(keyPrefix + ":")) {
                return keyPrefix + ":" + key;
            } else {
                return key;
            }
        } else {
            return key;
        }
    }
    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        setCacheObject(key,value,true);
    }
    public <T> void setCacheObject(final String key, final T value,boolean needPrefix)
    {
        redisTemplate.opsForValue().set(addKeyPrefix(key,needPrefix), value);
    }



    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final long timeout, final TimeUnit timeUnit)
    {
        setCacheObject(key, value, timeout, timeUnit,true);
    }
    public <T> void setCacheObject(final String key, final T value, final long timeout, final TimeUnit timeUnit,boolean needPrefix)
    {
        redisTemplate.opsForValue().set(addKeyPrefix(key,needPrefix), value, timeout, timeUnit);
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
        return expire(key, timeout, TimeUnit.SECONDS,true);
    }
    public boolean expire(final String key, final long timeout,boolean needPrefix)
    {
        return expire(key, timeout, TimeUnit.SECONDS,needPrefix);
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
        return expire(key, timeout, unit,true);
    }
    public boolean expire(final String key, final long timeout, final TimeUnit unit,boolean needPrefix)
    {
        return redisTemplate.expire(addKeyPrefix(key,needPrefix), timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key)
    {
        return getExpire(key,true);
    }
    public long getExpire(final String key,boolean needPrefix)
    {
        return redisTemplate.getExpire(addKeyPrefix(key,needPrefix));
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key)
    {
        return hasKey(key,true);
    }
    public Boolean hasKey(String key,boolean needPrefix)
    {
        return redisTemplate.hasKey(addKeyPrefix(key,needPrefix));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        return getCacheObject(key,true);
    }
    public <T> T getCacheObject(final String key,boolean needPrefix)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(addKeyPrefix(key,needPrefix));
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return deleteObject(key,true);
    }
    public boolean deleteObject(final String key,boolean needPrefix)
    {
        return redisTemplate.delete(addKeyPrefix(key,needPrefix));
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection)
    {
        return deleteObject(collection,true);
    }
    public long deleteObject(final Collection<String> collection,boolean needPrefix)
    {

        Collection<String> stringCollection = new ArrayList<>();
        collection.forEach((e)->{
            stringCollection.add(addKeyPrefix(e,needPrefix));
        });
        return redisTemplate.delete(stringCollection);
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

        return setCacheList(key,dataList,true);
    }
    public <T> long setCacheList(final String key, final List<T> dataList,boolean needPrefix)
    {
        Long count = redisTemplate.opsForList().rightPushAll(addKeyPrefix(key,needPrefix), dataList);
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
        return getCacheList(key,true);
    }
    public <T> List<T> getCacheList(final String key,boolean needPrefix)
    {
        return redisTemplate.opsForList().range(addKeyPrefix(key,needPrefix), 0, -1);
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
        return setCacheSet(key, dataSet,true);
    }
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet,boolean needPrefix)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(addKeyPrefix(key,needPrefix));
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return getCacheSet(key,true);
    }
    public <T> Set<T> getCacheSet(final String key,boolean needPrefix)
    {
        return redisTemplate.opsForSet().members(addKeyPrefix(key,needPrefix));
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        setCacheMap(key,dataMap,true);
    }
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap,boolean needPrefix)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(addKeyPrefix(key,needPrefix), dataMap);
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
        return getCacheMap(key,true);
    }
    public <T> Map<String, T> getCacheMap(final String key,boolean needPrefix)
    {
        return redisTemplate.opsForHash().entries(addKeyPrefix(key,needPrefix));
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
        setCacheMapValue(key,hKey,value,true);
    }
    public <T> void setCacheMapValue(final String key, final String hKey, final T value,boolean needPrefix)
    {
        redisTemplate.opsForHash().put(addKeyPrefix(key,needPrefix), hKey, value);
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
        return getCacheMapValue(key, hKey,true);
    }
    public <T> T getCacheMapValue(final String key, final String hKey,boolean needPrefix)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(addKeyPrefix(key,needPrefix), hKey);
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
        return getMultiCacheMapValue(key, hKeys,true);
    }
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys,boolean needPrefix)
    {
        return redisTemplate.opsForHash().multiGet(addKeyPrefix(key,needPrefix), hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }
    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Set<String> keys(final String pattern)
    {
        return keys(pattern,true);
    }
    public Set<String> keys(final String pattern,boolean needPrefix)
    {
        return redisTemplate.keys(addKeyPrefix(pattern,needPrefix));
    }

    /**
     * 将 key 所储存的值加上增量 increment 。
     * @param key
     * @param increment
     * @return
     */
    public long increment(String key,long increment)
    {
        return increment(key,increment,true);
    }

    public long increment(String key,long increment,boolean needPrefix)
    {
        return  redisTemplate.opsForValue().increment(key,increment);
    }
}
