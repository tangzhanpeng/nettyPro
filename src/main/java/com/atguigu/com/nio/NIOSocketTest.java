package com.atguigu.com.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOSocketTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8778);

        FileOutputStream fileOutputStream = new FileOutputStream("5.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(1);
        byteBuffers[1] = ByteBuffer.allocate(20);

        SocketChannel socketChannel = serverSocketChannel.accept();

        socketChannel.read(byteBuffers);
        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }
        outputStreamChannel.write(byteBuffers);
//        outputStreamChannel.close();
//        socketChannel.close();
//        serverSocketChannel.close();
    }
}
