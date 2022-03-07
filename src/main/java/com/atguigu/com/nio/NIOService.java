package com.atguigu.com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NIOService {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个selector对象
        Selector selector = Selector.open();
        //绑定端口6666，服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector，关心的时间是OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端链接
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1秒，无连接");
                continue;
            }
            //如果有事件发生,获取到事件相关的selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                //根据key对应的通道发生的事件做相应的处理
                if (selectionKey.isAcceptable()) {
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                //如果发生的是读事件
                else if (selectionKey.isReadable()) {
                    //通过key获取channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("from客户端"+byteBuffer.toString());
                }
                //手动从集合中移动当前的selectorKey
                selectionKeys.remove(selectionKey);
            }
        }
    }
}
