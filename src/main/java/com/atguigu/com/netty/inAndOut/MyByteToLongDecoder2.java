package com.atguigu.com.netty.inAndOut;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder2 decode 被调用");
        //不需要判断数据是否足够读取，内部会进行判断处理
        list.add(byteBuf.readLong());
    }
}
