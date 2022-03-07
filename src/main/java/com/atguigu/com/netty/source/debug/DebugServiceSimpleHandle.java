package com.atguigu.com.netty.source.debug;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DebugServiceSimpleHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("DebugServiceSimpleHandle channelRegistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("DebugServiceSimpleHandle channelActive");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("DebugServiceSimpleHandle handlerAdded");
    }
}
