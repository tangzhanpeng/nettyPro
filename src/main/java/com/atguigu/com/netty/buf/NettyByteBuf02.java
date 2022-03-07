package com.atguigu.com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            System.out.println(array);
            System.out.println(new String(array, CharsetUtil.UTF_8).trim());
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            int readableBytes = byteBuf.readableBytes();//可读长度
            System.out.println(readableBytes);

            System.out.println(byteBuf.getCharSequence(2,3,CharsetUtil.UTF_8));
        }
    }
}
