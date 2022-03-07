package com.atguigu.com.nio;

import java.nio.ByteBuffer;

public class NIOPutGet {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(100);
        byteBuffer.putLong(100L);
        byteBuffer.putChar('桑');
        byteBuffer.putShort((short) 4);

        //取出
        byteBuffer.flip();

        System.out.println();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());


    }
}
