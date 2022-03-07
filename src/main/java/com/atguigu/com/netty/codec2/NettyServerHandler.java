package com.atguigu.com.netty.codec2;

import com.atguigu.com.netty.codec.StudyPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 1.自定义handler，徐奥继承netty规定好的某个HandlerAdapter
 * 2.这是我们自定义个handler，才能成为一个handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //经数组写入缓存，并刷新
        //一般讲，哦们对这个发送的数据进行变吗
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～喵1", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage myMessage) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = myMessage.getStudent();
            System.out.println("学生id="+student.getId()+"名字="+student.getName());
        } else if(dataType == MyDataInfo.MyMessage.DataType.WorkType) {
            MyDataInfo.Worker worker = myMessage.getWorker();
            System.out.println("工人年龄="+worker.getAge()+"名字="+worker.getName());
        } else {
            System.out.println("传输数据类型错误");
        }
    }
}
