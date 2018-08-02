package com.xuxiang.study.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

/**
 * json相关util
 *
 * @author caohu.ch
 * @since 20151111
 */
public final class JSONUtil {
    // 这里不能用此MapperFeature.USE_STATIC_TYPING，因为在转换时有非静态类的转换可能性，例如转换数组
    // 虽然可以提高转换速度
    private final static ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DEFAULT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DEFAULT_MAPPER.setVisibilityChecker(DEFAULT_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    /**
     * 将字符串转化为对象
     *
     * @param jsonString 字符串
     * @param clazz      目标对象类名
     * @param <T>        模版类
     * @return object
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        try {
            return DEFAULT_MAPPER.readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串转化为模版类型对象
     *
     * @param jsonString    字符串
     * @param typeReference 目标模版类型
     * @param <T>           模版
     * @return object
     */
    public static <T> T fromJsonString(String jsonString, TypeReference<T> typeReference) {
        try {
            return DEFAULT_MAPPER.readValue(jsonString, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转化为json字符串
     *
     * @param object object
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return DEFAULT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    /**
     * 将json字符串转化为Map
     *
     * @param jsonString json字符串
     * @param <T>        模版
     * @return Map对象
     */
    public static <T extends Map> T toMap(String jsonString) {
        return (T) fromJsonString(jsonString, Map.class);
    }
}
