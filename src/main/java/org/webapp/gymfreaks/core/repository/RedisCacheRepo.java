// package org.webapp.gymfreaks.core.repository;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Set;

// public interface RedisCacheRepo<V> {

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value Integer value
// */
// void saveString(String key, String value);

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional String
// */
// Optional<String> getString(String key);

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value Set<String> value
// */
// void saveSet(String key, Set<V> value);

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional Set<String>
// */
// Optional<Set<V>> getSet(String key);

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value List<String> value
// */
// void saveList(String key, List<V> value);

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional List<String>
// */
// Optional<List<V>> getList(String key);

// /**
// * Store the key value pair in cache
// *
// * @param key String key
// * @param value Map<String, String> value
// */
// void saveHash(String key, Map<String, V> value);

// /**
// * Retrieve value from cache when key is provided
// *
// * @param key String key
// * @return Optional Map<String, String>
// */
// Optional<Map<String, V>> getHash(String key);

// /**
// * Remove value from cache
// *
// * @param key String key
// */
// void removeObject(String key);

// }
