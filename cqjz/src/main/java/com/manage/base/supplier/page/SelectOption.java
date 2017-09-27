package com.manage.base.supplier.page;

/**
 * Created by bert on 2017/9/3.
 */
public class SelectOption<K, V> {

    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
