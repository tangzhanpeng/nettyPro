package com.atguigu.com.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyService {
    public static void main(String[] args) throws InterruptedException {
        /*
          1、创建两个线程组bossGroup，workerGroup
          2、bossGroup只处理连接请求，真正和客户端业务处理，交给workerGroup完成
          3、两个都是无限循环
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(6);

        try {
            //创建服务端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println(".....服务器 is ready ..");
            //绑定一个端口并且同步，生成一个ChannelFuture对象
            //启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //给cf注册监听器，监控我们关心的事件
            cf.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("监听端口6668成功");
                } else {
                    System.out.println("监听端口6668失败");
                }
            });
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
