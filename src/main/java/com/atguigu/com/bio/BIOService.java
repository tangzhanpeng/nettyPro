package com.atguigu.com.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOService {

    public static void main(String[] args) throws IOException {
        // 创建自定义线程池
        ThreadPoolExecutor socketExecutor = new ThreadPoolExecutor(1,
                2,
                10,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(3));
        ServerSocket serverSocket = new ServerSocket(6666);
        while(true) {
            final Socket socket = serverSocket.accept();
            System.out.println("链接到一个客户端");

            socketExecutor.execute(() -> {
                //和客户端通信
                handler(socket);
            });
        }

    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程id="+Thread.currentThread().getId()+"名字="+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程id="+Thread.currentThread().getId()+"名字="+Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
