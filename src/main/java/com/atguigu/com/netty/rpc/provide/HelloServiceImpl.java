package com.atguigu.com.netty.rpc.provide;

import com.atguigu.com.netty.rpc.common.HelloService;

public class HelloServiceImpl implements HelloService {
    private int count;
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息="+msg);
        if (msg != null) {
            return "你好客户端，我已经收到你的消息【"+msg+"】"+(++count);
        } else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
