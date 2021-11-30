package netty.protobuf2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.Mymessage mymessage) throws Exception {
        //学生对象
        switch (mymessage.getDataType()) {
            case WorkerType:
                MyDataInfo.Worker worker = mymessage.getWorker();
                System.out.println("工人信息:姓名：" + worker.getName() + " age:" + worker.getAge());
                break;
            case StudentType:
                MyDataInfo.Student student = mymessage.getStudent();
                System.out.println("学生信息:姓名：" + student.getName() + " id:" + student.getId());
                break;
            default:
                System.out.println("传送数据格式不对!");
                break;
        }

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 (>^ω^<)喵", CharsetUtil.UTF_8));

    }
    //处理异常 一般关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
