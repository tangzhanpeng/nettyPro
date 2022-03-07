package com.atguigu.com.netty.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private final static ExecutorService executor = new ThreadPoolExecutor(
            8,
            8,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(108));

    private static NettyClientHandler client;

    public Object getBean(final Class<?> serverClass, final String provider) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                , new Class<?>[]{serverClass}
                , (proxy, method, args) -> {
                    if (client == null) {
                        initClient();
                    }
                    client.setParam(provider + args[0]);
                    return executor.submit(client).get();
                });
    }

    private static void initClient() {
        client = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(client);
                        }
                    });
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 9000).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
