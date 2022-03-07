package com.atguigu.com.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte)i);
        }
        buffer.flip();

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

        while(readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte)10);
    }
}
