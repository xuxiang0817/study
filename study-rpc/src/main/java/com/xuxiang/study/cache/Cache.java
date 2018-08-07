package com.xuxiang.study.cache;

/**
 * Cache
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);

    void clear();
}
