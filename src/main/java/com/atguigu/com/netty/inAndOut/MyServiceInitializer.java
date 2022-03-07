package com.atguigu.com.netty.inAndOut;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServiceInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码MyByteToLogDecoder
        //pipeline.addLast(new MyByteToLogDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        pipeline.addLast(new MyLongToByteEncode());
        pipeline.addLast(new MyServiceHandler());
    }

}
