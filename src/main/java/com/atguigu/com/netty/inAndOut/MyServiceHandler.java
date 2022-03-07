package com.atguigu.com.netty.inAndOut;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServiceHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端读到" + ctx.channel().remoteAddress() + "读取到long=" + msg);

        //发送消息给客户端
        ctx.writeAndFlush(654321L);
    }
}
