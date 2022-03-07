package com.atguigu.com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建对象，该对象包含一个数组arr -> byte[10]
        //在netty的buffer中，不需要flip进行读写反转
        //底层喂了一个readerIndex,writerIndex
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capacity="+buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));//可重复读
            System.out.println(buffer.readByte());//一次性读
        }
    }
}
