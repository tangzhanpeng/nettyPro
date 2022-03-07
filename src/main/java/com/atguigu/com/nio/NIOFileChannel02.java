package com.atguigu.com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        File file = new File("/Users/tangzhanpeng/Desktop/hello01.txt");
        FileInputStream fileInputStream =
                new FileInputStream("/Users/tangzhanpeng/Desktop/hello01.txt");
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        //神罗天征
        channel.read(byteBuffer);

        //将byteBuffer的字节转为String
        System.out.println(new String(byteBuffer.array()));

    }
}
