package com.xuxiang.study.generic;

/**
 * InfoImpl
 *
 * @author xuxiang
 * @since 16/5/22
 */
public class InfoImpl<T,K,U> implements Info<T>{
    private K x;
    private U y;

    @Override
    public T getVar() {
        return null;
    }

    @Override
    public void setVar(T var) {

    }
}
