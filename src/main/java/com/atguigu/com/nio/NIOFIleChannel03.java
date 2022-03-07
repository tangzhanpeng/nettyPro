package com.atguigu.com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFIleChannel03 {
    public static void main(String[] args) throws Exception {
//        File file = new File("1.txt");
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            //需要吧缓冲区清空
            byteBuffer.clear();
            int read = fileChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //buffer切换为写
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }

        fileInputStream.close();
        outputStreamChannel.close();
    }
}
