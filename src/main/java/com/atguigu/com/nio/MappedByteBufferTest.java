package com.atguigu.com.nio;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * MappedByteBuffer可以让文件可以直接在内存中修改（堆外内存），
 * 即操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");

        FileChannel channel = rw.getChannel();

        /**
         * FileChannel.MapMode.READ_WRITE 读写模式
         * 0： 可以直接修改的岂是大小
         * 5： 映射到内存的大小，即将1.txt的多少个字节映射到内存
         * 可以直接修改的范围是[0,5)
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)'9');

        rw.close();
    }
}
