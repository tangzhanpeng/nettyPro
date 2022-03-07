package com.atguigu.com.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServiceHandler extends SimpleChannelInboundHandler<String> {

    //GlobalEventExecutor.INSTANCE全局的事件执行器，是一个单例
    private final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String getNowTime() {
        return SDF.format(new Date());
    }

    /**
     * 表示连接建立，一旦连接，第一个被执行
     * 将当前的channel加入CHANNEL_GROUP
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /*
        将该客户加入聊天的消息推送给其它在线的客户端
        该方法会发送给所有的channel,不需要自己遍历
         */
        CHANNEL_GROUP.writeAndFlush(getNowTime() + "[客户端]" + channel.remoteAddress() + "加入聊天\n");
        CHANNEL_GROUP.add(channel);
        System.out.println("线程："+Thread.currentThread().getName() + " hashcode="+this.hashCode());
    }

    /**
     * 断开连接
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        CHANNEL_GROUP.writeAndFlush(getNowTime() + "[客户端]" + channel.remoteAddress() + "离开聊天\n");
        //CHANNEL_GROUP.remove(channel);
        System.out.println("当前在线人数：" + CHANNEL_GROUP.size());
    }

    /**
     * 表示channel处于活动状态，提示xx上线
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getNowTime() + ctx.channel().remoteAddress() + "上线了～");
    }

    /**
     * 处于非活跃状态
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getNowTime() + ctx.channel().remoteAddress() + "离线了～");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        System.out.println(getNowTime() + ctx.channel().remoteAddress() + "发生异常");
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //遍历，根据不同的情况回送不同的消息
        CHANNEL_GROUP.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(getNowTime() + "[客户]" + channel.remoteAddress() + " 发送消息：" + msg + "\n");
            } else {
                ch.writeAndFlush(getNowTime() + "[自己]发送了消息：" + msg + "\n");
            }
        });
    }
}
