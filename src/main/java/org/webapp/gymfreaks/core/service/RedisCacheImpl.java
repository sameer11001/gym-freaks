// package org.webapp.gymfreaks.core.service;

// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Set;
// import java.util.concurrent.TimeUnit;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.redis.core.HashOperations;
// import org.springframework.data.redis.core.ListOperations;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.core.SetOperations;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.data.redis.core.ValueOperations;
// import org.springframework.stereotype.Component;
// import org.webapp.gymfreaks.core.config.CustomLogger;
// import org.webapp.gymfreaks.core.error.OTPServiceException;
// import org.webapp.gymfreaks.core.repository.RedisCacheRepo;

// @Component
// public class RedisCacheImpl<V> implements RedisCacheRepo<V> {

// @Autowired
// private RedisTemplate<String, V> objectTemplate;
// @Autowired
// private StringRedisTemplate stringRedisTemplate;
// private long ttl;
// private ValueOperations<String, String> stringValueOps;
// private ListOperations<String, V> listOps;
// private SetOperations<String, V> setOps;
// private HashOperations<String, String, V> hashOps;

// public RedisCacheImpl(
// @Value("${redis.timeToLive}") long ttl, RedisTemplate<String, V>
// redisTemplate) {
// this.objectTemplate = redisTemplate;
// this.stringValueOps = this.stringRedisTemplate.opsForValue();
// listOps = objectTemplate.opsForList();
// setOps = objectTemplate.opsForSet();
// hashOps = objectTemplate.opsForHash();
// this.ttl = ttl;
// }

// /**
// * Save the key value pair in cache with a ttl
// *
// * @param key cache key
// * @param value cache value
// */
// @Override
// public void saveString(String key, String value) {
// try {
// stringValueOps.set(key, String.valueOf(value));
// stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
// CustomLogger.debug("value saved in cache");
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while saving to cache ", e);
// }
// }

// /**
// * Returns the cached value
// *
// * @param key cached key
// * @return cached value
// */
// @Override
// public Optional<String> getString(String key) {
// try {
// Boolean b = objectTemplate.hasKey(key);
// if (Boolean.TRUE.equals(b)) {
// return Optional.ofNullable(stringValueOps.get(key));
// } else {
// CustomLogger.debug("Key not found in cache");
// return Optional.empty();
// }
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while retrieving from the cache ", e);
// }
// }

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value List<String> value
// */
// @Override
// public void saveList(String key, List<V> value) {
// try {

// listOps.leftPushAll(key, value);
// objectTemplate.expire(key, ttl, TimeUnit.SECONDS);
// CustomLogger.debug("List saved in cache");

// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while saving to cache ", e);
// }
// }

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional List<String>
// */
// @Override
// public Optional<List<V>> getList(String key) {
// try {
// Boolean b = objectTemplate.hasKey(key);
// if (Boolean.TRUE.equals(b)) {
// return Optional.ofNullable(listOps.range(key, 0, -1));
// } else {
// CustomLogger.debug("Key not found in cache");
// return Optional.empty();
// }
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while retrieving from the cache ", e);
// }
// }

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value Set<String> value
// */
// @SuppressWarnings("unchecked")
// @Override
// public void saveSet(String key, Set<V> value) {
// try {
// setOps.add(key, (V[]) value.toArray(new Object[0]));
// objectTemplate.expire(key, ttl, TimeUnit.SECONDS);
// CustomLogger.debug("Set saved in cache");
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while saving to cache ", e);
// }
// }

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional Set<String>
// */
// @Override
// public Optional<Set<V>> getSet(String key) {
// try {
// Boolean b = objectTemplate.hasKey(key);
// if (Boolean.TRUE.equals(b)) {
// return Optional.ofNullable(new HashSet<>(setOps.members(key)));
// } else {
// CustomLogger.debug("Key not found in cache");
// return Optional.empty();
// }
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while retrieving from the cache ", e);
// }
// }

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value Map<HK String,HV String> value
// */
// @Override
// public void saveHash(String key, Map<String, V> value) {
// try {
// hashOps.putAll(key, value);
// objectTemplate.expire(key, ttl, TimeUnit.SECONDS);
// CustomLogger.debug("Hash saved in cache");
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while saving to cache ", e);
// }
// }

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional Map<String, String>
// */
// @Override
// public Optional<Map<String, V>> getHash(String key) {
// try {
// Boolean b = objectTemplate.hasKey(key);
// if (Boolean.TRUE.equals(b)) {
// return Optional.ofNullable(hashOps.entries(key));

// } else {
// CustomLogger.debug("Key not found in cache");
// return Optional.empty();
// }
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while retrieving from the cache ", e);
// }
// }

// /**
// * Remove the cached value
// *
// * @param key cached key
// */
// @Override
// public void removeObject(String key) {
// try {
// objectTemplate.delete(key);
// } catch (RuntimeException e) {
// throw new OTPServiceException("Error while removing from the cache ", e);
// }
// }

// }
