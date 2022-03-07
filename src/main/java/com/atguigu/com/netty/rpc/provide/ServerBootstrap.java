package com.atguigu.com.netty.rpc.provide;

import com.atguigu.com.netty.rpc.netty.NettyServer;

public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 9000);
    }
}
