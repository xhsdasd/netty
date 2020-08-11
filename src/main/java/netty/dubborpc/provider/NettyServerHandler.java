package netty.dubborpc.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg.toString());
        //客户端在调用服务器的api时，我们需要定义一个协议
        //以HelloServer#开头
        if(msg.toString().startsWith("HelloServer#")){
            ctx.writeAndFlush(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
    }
}
