package com.atguigu.com.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String param;

    //被代理对象调用，发送数据给服务区，wait->等待被唤醒-》返回结果
    @Override
    public synchronized Object call() throws Exception {
        System.out.println(" call1 被调用  ");
        if (context == null) {
            wait();
        }
        context.writeAndFlush(param);
        wait();
        return result;
    }

    @Override
    public synchronized void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" channelActive 被调用  ");
        this.context = ctx;
        notify();
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead 被调用  ");
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常"+cause.getMessage());
    }

    void setParam(String param) {
        System.out.println(" setPara  ");
        this.param = param;
    }
}
