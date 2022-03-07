package com.atguigu.com.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 1.自定义handler，徐奥继承netty规定好的某个HandlerAdapter
 * 2.这是我们自定义个handler，才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据
     *
     * @param ctx 上下文对象，含有管道pipeline,通道channel,地址
     * @param msg 就是客户端发送的数据
     * @throws Exception 哈哈
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        //解决方案1 用户自定义普通任务
        ctx.channel().eventLoop().execute(() -> {
            System.out.println("服务器异步线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～喵2", CharsetUtil.UTF_8));
        });
        ctx.channel().eventLoop().execute(() -> {
            System.out.println("服务器异步线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～喵3", CharsetUtil.UTF_8));
        });

        //用户自定义定时任务-> 该任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(() -> {
            System.out.println("服务器异步线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～喵4", CharsetUtil.UTF_8));
        }, 5, TimeUnit.SECONDS);
        System.out.println("go on ....");
//        super.channelRead(ctx, msg);
//        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
//        System.out.println("server ctx=" + ctx);
//        System.out.println("channel和pipeline的关系：");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//        //将msg转为一个ByteBuf
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //经数组写入缓存，并刷新
        //一般讲，哦们对这个发送的数据进行变吗
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～喵1", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
