package com.atguigu.com.netty.protocoltcp;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol message, ByteBuf byteBuf) throws Exception {
        System.out.println("MyMessageEncoder encode 方法被调用");
        byteBuf.writeInt(message.getLen());
        byteBuf.writeBytes(message.getContent());
    }
}
