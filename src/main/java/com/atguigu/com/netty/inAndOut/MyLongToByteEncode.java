package com.atguigu.com.netty.inAndOut;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncode extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncode encode 被调用");
        System.out.println("msg="+aLong);
        byteBuf.writeLong(aLong);
    }
}
