package com.atguigu.com.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        //创建客户端启动对象
        //是Bootstrap
        try {
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(eventExecutors) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道参数（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 ok");
            ChannelFuture channelFuture = bootstrap.connect("10.20.0.35", 9876).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
