package com.atguigu.com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler是 ChannelInboundHandlerAdapter子类
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param ctx 上下文
     * @param msg  客户端和副端通信的消息背封装成HttpObject
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型:" + msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());

            //获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            //获取uri
            URI uri = new URI(httpRequest.uri());

            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了favicon.ico不做响应");
                return;
            }

            //回复信息给浏览器【http协议】
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http响应，即httpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
