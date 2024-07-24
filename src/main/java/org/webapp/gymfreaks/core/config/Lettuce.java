package org.webapp.gymfreaks.core.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.core.error.OTPServiceException;

@Service
public class Lettuce<V> {

    private long ttl;
    private final RedisTemplate<String, V> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    private ValueOperations<String, String> stringValueOps;
    private ListOperations<String, V> listOps;
    private SetOperations<String, V> setOps;
    private HashOperations<String, String, V> hashOps;

    public Lettuce(RedisTemplate<String, V> redisTemplate, StringRedisTemplate stringRedisTemplate,
            @Value("${redis.timeToLive}") long ttl) {
        this.ttl = ttl;
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
        this.stringValueOps = this.stringRedisTemplate.opsForValue();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
        this.hashOps = redisTemplate.opsForHash();
    }

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */
    public void saveString(String key, String value) {
        stringValueOps.set(key, value);
        stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return Optional String
     */
    public Optional<String> getString(String key) {
        if (Boolean.TRUE.equals(keyStringExists(key))) {
            return Optional.ofNullable(stringValueOps.get(key));

        }
        return Optional.empty();
    }

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */
    public void saveObject(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return Optional String
     */
    public Optional<V> getObject(String key) {
        if (Boolean.TRUE.equals(keyObjectExists(key))) {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key));
        }
        return Optional.empty();
    }

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */
    public void saveList(String key, List<V> value) {

        listOps.leftPushAll(key, value);
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return Optional String
     */
    public Optional<List<V>> getList(String key) {
        if (Boolean.TRUE.equals(keyObjectExists(key))) {
            return Optional.ofNullable(listOps.range(key, 0, -1));
        }
        return Optional.empty();
    }

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */

    @SuppressWarnings("unchecked")
    public void saveSet(String key, Set<V> value) {

        setOps.add(key, (V[]) value.toArray(new Object[0]));
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return Optional String
     */
    public Optional<Set<V>> getSet(String key) {
        if (Boolean.TRUE.equals(keyObjectExists(key))) {
            return Optional.ofNullable(new HashSet<>(setOps.members(key)));
        }
        return Optional.empty();
    }

    /**
     * Save the key value pair in cache with a ttl
     *
     * @param key   cache key
     * @param value cache value
     */
    public void saveHash(String key, Map<String, V> value) {
        hashOps.putAll(key, value);
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * Returns the cached value
     *
     * @param key cached key
     * @return Optional String
     */
    public Optional<Map<String, V>> getHash(String key) {
        if (Boolean.TRUE.equals(keyObjectExists(key))) {
            return Optional.ofNullable(hashOps.entries(key));
        }

        return Optional.empty();
    }

    Boolean keyStringExists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    Boolean keyObjectExists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void removeObject(String key) {
        try {
            redisTemplate.delete(key);
        } catch (RuntimeException e) {
            throw new OTPServiceException("Error while removing from the cache ", e);
        }
    }
}
