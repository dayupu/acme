package com.manage.cache.bean;


public class LocalCache<V> {
    private V value;
    private long timeoutMillis = -1;

    public LocalCache(V value) {
        this.value = value;
    }

    public LocalCache(V value, long ttl) {
        this.value = value;
        this.timeoutMillis = extendTimoutMillis(ttl);
    }

    public V value() {
        return value;
    }

    public long timeoutMillis() {
        return timeoutMillis;
    }

    public boolean isExpired() {
        if (timeoutMillis > 0 && timeoutMillis < currentMillis()) {
            return true;
        }
        return false;
    }

    public LocalCache extendTTL(long ttl){
        this.extendTimoutMillis(ttl);
        return this;
    }

    private long currentMillis() {
        return System.currentTimeMillis();
    }

    private long extendTimoutMillis(long ttl) {
        return System.currentTimeMillis() + ttl * 1000;
    }
}
