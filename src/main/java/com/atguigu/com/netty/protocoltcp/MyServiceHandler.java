package com.atguigu.com.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServiceHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息="+cause.getMessage());
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到的信息如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收到的消息包数量="+(++count));
        String responseContent = UUID.randomUUID().toString();
        byte[] bytes = responseContent.getBytes(CharsetUtil.UTF_8);
        int reLen = bytes.length;
        ctx.writeAndFlush(new MessageProtocol(reLen, bytes));
    }
}
