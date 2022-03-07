package com.atguigu.com.netty.rpc.customer;

import com.atguigu.com.netty.rpc.common.HelloService;
import com.atguigu.com.netty.rpc.netty.NettyClient;

public class ClientBootstrap {
    public static final String provider = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        HelloService helloServiceProxy = (HelloService) nettyClient.getBean(HelloService.class, provider);
//        String result = helloServiceProxy.hello("你好rpc");
//        System.out.println("服务端调用的结果"+result);
        for (;;) {
            Thread.sleep(10 * 1000);
            System.out.println("服务端调用的结果"+helloServiceProxy.hello("你好rpc"));
            System.out.println();
        }
    }
}
