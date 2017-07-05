package com.manage.cache;


public interface CacheManager<K, V> {

    public void put(K key, V obj);

    public void put(K key, V obj, long ttl);

    public Integer append(K key, String obj);

    public V get(K key);

}
