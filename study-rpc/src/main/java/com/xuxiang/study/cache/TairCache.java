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
     * @param expireTime ����ʱ�� seconds
     */
    void put(K k, V v, int expireTime);

    /**
     *
     * @param keys Key�б�
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
     * @param expireTime ����ʱ�� seconds
     */
    void mput(Map<K, V> beans, int expireTime);

    /**
     *
     * @param k key
     * @param value value
     * @param defaultValue key������ʱ���õ�ֵ
     * @return �������Ӻ��ֵ, ���Ϊnull���ʾʧ��
     */
    Integer incNumber(K k, int value, int defaultValue);

    /**
     *
     * @param k key
     * @param value value
     * @param defaultValue key������ʱ���õ�ֵ
     * @param expireSeconds ����ʱ�� seconds
     * @return �������Ӻ��ֵ, ���Ϊnull���ʾʧ��
     */
    Integer incNumber(K k, int value, int defaultValue, int expireSeconds);

    /**
     *
     * @param k key
     * @return ͨ��increment ���õ�ֵ
     */
    Integer getNumber(K k);
}
