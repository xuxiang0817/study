package com.xuxiang.study.generic;

import java.util.List;

/**
 * StaticFans
 *
 * @author xuxiang
 * @since 16/5/22
 */
public class StaticFans {

    public static <T> void staticMethod(T a){

    }

    public <T> void otherMethod(T a){

    }

    public static <T> List<T> parseArray(String reponse, Class<T> obj){
        return null;
    }

    public static <T> T[] fun1(T... arg){
        return arg;
    }

}
