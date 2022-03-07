package com.atguigu.com.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            if (!socketChannel.connect(new InetSocketAddress(HOST, PORT))) {
              while (!socketChannel.finishConnect()) {
                  System.out.println("等待服务端连接");
              }
            };
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString();
            System.out.println(username +"is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(String info) {
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels == 0) {
                System.out.println("客户端等待读取数据");
                return;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                String msg = new String(buffer.array());
                System.out.println(msg.trim());
                selectionKeys.remove(selectionKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }


}
