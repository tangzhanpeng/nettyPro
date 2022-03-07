package com.atguigu.com.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String msg = "今天天气冷，吃火锅";
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int length = content.length;
            MessageProtocol messageProtocol = new MessageProtocol(length, content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息="+cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到的信息如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content, CharsetUtil.UTF_8));
        System.out.println("客户端接收到的消息包数量="+(++count));
    }

}
