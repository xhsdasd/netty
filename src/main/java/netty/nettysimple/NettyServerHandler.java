package netty.nettysimple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的信息是:"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:"+ctx.channel().remoteAddress());

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
