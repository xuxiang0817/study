package com.xuxiang.study.rpc;

/**
 * Created by xuxiang on 15/11/24.
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception{
        HelloService helloService = new HelloServiceImpl();
        RpcFramework.export(helloService, 1234);
    }
}
