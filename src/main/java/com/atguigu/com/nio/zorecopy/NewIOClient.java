package com.atguigu.com.nio.zorecopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename = "/Users/tangzhanpeng/Desktop/my/婚礼/流程.mp4";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        long startTime = System.currentTimeMillis();

        //底层使用的就是零拷贝
        long transferTo = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数="+transferTo+" 耗时："+(System.currentTimeMillis() - startTime));
    }
}
