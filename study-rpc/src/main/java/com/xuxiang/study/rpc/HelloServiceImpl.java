package com.xuxiang.study.rpc;

/**
 * Created by xuxiang on 15/11/24.
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
