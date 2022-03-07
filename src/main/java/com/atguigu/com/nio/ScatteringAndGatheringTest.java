package com.atguigu.com.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入【分散】
 * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        //使用ServerSocketChannel建立通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8777);

        FileOutputStream fileOutputStream = new FileOutputStream("3.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer3 = ByteBuffer.allocate(100);
//        byteBuffer3.put("sjdfajs".getBytes());
//        byteBuffer3.flip();
//        outputStreamChannel.write(byteBuffer3);

        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等客户端链接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        //循环的读取

        int byteRead = 0;

            long read = socketChannel.read(byteBuffers);
            byteRead += read;
            System.out.println("byteRead=" + byteRead);
            //使用流打印，看看当前的这个buffer的position和limit
            for (ByteBuffer byteBuffer : byteBuffers) {
                System.out.println("position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit());
                byteBuffer.flip();
            }
            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                outputStreamChannel.write(byteBuffers);
                byteWrite += socketChannel.write(byteBuffers);
            }
            //将所有的buffer 进行clear
            for (ByteBuffer buffer : byteBuffers) {
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }

            System.out.println("byteRead:=" + byteRead + "byteWrite=" + byteWrite + ",messageLength" + messageLength);

        outputStreamChannel.close();
        fileOutputStream.close();
        socketChannel.close();
    }
}
