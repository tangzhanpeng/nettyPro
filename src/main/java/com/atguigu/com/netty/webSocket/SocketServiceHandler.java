package com.atguigu.com.netty.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 说明：
 * 1、TextWebSocketFrame表示一个文本帧（frame）
 */
public class SocketServiceHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当web客户端连接后，触发该方法
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //longText是唯一的
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText() + " " + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常，关闭通道" + cause.getMessage());
        ctx.channel().close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText() + " " + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务端收到消息" + msg.text());
        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg.text()));
    }

}
