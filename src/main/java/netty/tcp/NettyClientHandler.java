package netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    //就绪状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("发送数据");
        for (int i = 0; i < 5; i++) {
            String msg = "太冷了啊我要穿棉袄" + i;
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            Message message = new Message(bytes.length, bytes);
            ctx.writeAndFlush(message);
        }

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message aLong) throws Exception {
        System.out.println("收到服务器信息:" + aLong);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
