package com.atguigu.com.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);//5指的是可以存放5个int
        //存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //读取数据
        //读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
