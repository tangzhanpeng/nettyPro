package com.atguigu.com.netty.inAndOut;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLogDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLogDecoder decode 被调用");
        if (byteBuf.readableBytes() >= 8) {
            list.add(byteBuf.readLong());
        }
    }

}
