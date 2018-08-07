package com.xuxiang.study.cache;

import java.util.List;
import java.util.Map;

/**
 * TairCache
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public interface TairCache<K, V> extends Cache<K, V>{

    /**
     *
     * @param k key
     * @param v value
     * @param expireTime 过期时间 seconds
     */
    void put(K k, V v, int expireTime);

    /**
     *
     * @param keys Key列表
     * @return key,value Map
     */
    Map<K, V> mget(List<K> keys);

    /**
     *
     * @param beans objects
     */
    void mput(Map<K, V> beans);

    /**
     *
     * @param beans objects
     * @param expireTime 过期时间 seconds
     */
    void mput(Map<K, V> beans, int expireTime);

    /**
     *
     * @param k key
     * @param value value
     * @param defaultValue key不存在时设置的值
     * @return 返回增加后的值, 如果为null则表示失败
     */
    Integer incNumber(K k, int value, int defaultValue);

    /**
     *
     * @param k key
     * @param value value
     * @param defaultValue key不存在时设置的值
     * @param expireSeconds 过期时间 seconds
     * @return 返回增加后的值, 如果为null则表示失败
     */
    Integer incNumber(K k, int value, int defaultValue, int expireSeconds);

    /**
     *
     * @param k key
     * @return 通过increment 设置的值
     */
    Integer getNumber(K k);
}
