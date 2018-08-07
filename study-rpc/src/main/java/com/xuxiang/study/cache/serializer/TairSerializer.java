package com.xuxiang.study.cache.serializer;

/**
 * TairSerializer
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public interface TairSerializer<T> {

    String serialize(T v);

    T deserialize(String v);
}
