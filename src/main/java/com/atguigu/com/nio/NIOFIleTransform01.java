package com.atguigu.com.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFIleTransform01 {

    public static void main(String[] args) throws Exception{
        //创建流
        FileInputStream fileInputStream = new FileInputStream("/Users/tangzhanpeng/Desktop/IMG_0232.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/tangzhanpeng/Desktop/tzp.jpg");

        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();

        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        sourceChannel.close();
        destChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
