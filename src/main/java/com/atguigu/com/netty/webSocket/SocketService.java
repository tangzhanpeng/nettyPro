package com.atguigu.com.netty.webSocket;

import com.atguigu.com.netty.heartbeat.MyServiceHeartHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class SocketService {
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
                    //因为是基于http协议，使用http的编码和解码器
                    pipeline.addLast(new HttpServerCodec());
                    //是以块方式写，添加chunkedWrite处理器
                    pipeline.addLast(new ChunkedWriteHandler());
                    /*
                     * 说明
                     * 1、http数据阻碍传输过程中是分段的，HttpObjectAggregator，就是可以将多个段聚合
                     * 2、这就是为什么，浏览器发送大量数据时，就会发送多次http请求
                     */
                    pipeline.addLast(new HttpObjectAggregator(8192));
                    /*
                     * 说明：
                     * 1、对应的websocket，数据是一帧（frame）的形式传递的
                     * 1、可以看到webSocketFrame下面有六个子类
                     * 3、浏览器请求时ws://localhost:7000/xxx表示请求的uri
                     * 4、WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议，保持长连接
                     */
                    pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                    //自定义的处理器，处理业务逻辑
                    //pipeline.addLast(new SocketExceptionHandler());
                    pipeline.addLast(new SocketServiceHandler());
                }
            });
            System.out.println("netty socket测试服务器启动");
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
