package com.atguigu.com.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello,尚硅谷";
        String str2 = "hello,尚硅谷2222";

        FileOutputStream fileOutputStream =
                new FileOutputStream("/Users/tangzhanpeng/Desktop/hello03.txt");

        //真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区ByteBuffer
        //ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(1024);
        byteBuffers[1] = ByteBuffer.allocate(1024);

        //将str放入byteBuffer;
        byteBuffers[0].put(str.getBytes(StandardCharsets.UTF_8));
        byteBuffers[1].put(str2.getBytes(StandardCharsets.UTF_8));

        //切换为读
        byteBuffers[0].flip();
        byteBuffers[1].flip();

        //将缓冲区的数据写入到channel
        //万象天引
        fileChannel.write(byteBuffers);
        fileOutputStream.close();
    }
}
