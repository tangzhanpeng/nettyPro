package com.atguigu.com.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecode extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecode decode 方法被调用");
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        MessageProtocol messageProtocol = new MessageProtocol(length, bytes);
        list.add(messageProtocol);
    }
}
