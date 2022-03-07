package com.atguigu.com.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyService {
    public static void main(String[] args) {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    /*
                     IdleStateHandler空闲状态处理器
                     1、long readerIdleTime, 表示多长事件没有读，就会发生一个心跳检测包检测是否连接
                     2、long writerIdleTime, 表示多长事件没有写，就会发生一个心跳检测包检测是否连接
                     3、long allIdleTime， 表示多长事件没有读写，就会发生一个心跳检测包检测是否连接
                     4、当事件触发后，就会通过管道传递给下一个处理器处理
                     userEventTriggered
                     */
                    pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                    //加入一个对空闲检测进一步处理的自定义处理器
                    pipeline.addLast(new MyServiceHeartHandler());
                }
            });
            System.out.println("netty 心跳检测测试服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(7002).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
