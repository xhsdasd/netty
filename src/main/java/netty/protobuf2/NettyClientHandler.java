package netty.protobuf2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //就绪状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //创建MyDataInfo对象
        int i = new Random().nextInt(3);
        MyDataInfo.Mymessage mymessage = null;
        System.out.println(i);
        if (0 == i) {//学生
            mymessage = MyDataInfo.Mymessage.newBuilder().setDataType(MyDataInfo.Mymessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("尼玛同学").build()).build();
        } else { //工人
            mymessage = MyDataInfo.Mymessage.newBuilder().setDataType(MyDataInfo.Mymessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("尼玛工人").build()).build();

        }
        ctx.writeAndFlush(mymessage);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的信息:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址:" + ctx.channel().remoteAddress());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
