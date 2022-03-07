package com.atguigu.com.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class GroupChatService {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //初始化工作
    public GroupChatService() {
        try {
            //得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {
            //循环处理
            while (true) {
                int count = selector.select();
                if (count == 0) {
                    System.out.println("等待。。。");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    //监听到accept
                    if (key.isAcceptable()) {
                        SocketChannel sc = listenChannel.accept();
                        sc.configureBlocking(false);
                        //将sc注册
                        sc.register(selector, SelectionKey.OP_READ);
                        System.out.println(sc.getRemoteAddress() + "上线");
                    } else if (key.isReadable()) {
                        //处理客户端读取
                        readData(key);
                    }
                    selectionKeys.remove(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取客户端的消息
    private void readData(SelectionKey key) {
        //第一一个socketChannel
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from客户端：" + msg.trim());
                //向其它的客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //消息转发
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中。。。");
        //便利所哟注册到selector上的socketChannel,并排除自己
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof  SocketChannel && targetChannel != self) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer数据写入通道
                socketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatService groupChatService = new GroupChatService();
        groupChatService.listen();
    }
}
