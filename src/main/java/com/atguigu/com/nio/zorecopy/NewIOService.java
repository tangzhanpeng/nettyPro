package com.atguigu.com.nio.zorecopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOService {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteBuffer.rewind();
            }
        }


    }
}
