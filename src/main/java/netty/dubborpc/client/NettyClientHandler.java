package netty.dubborpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;//上下文
    private String result;//返回的结果
    private String parm;//调用传递的参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context=ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       result=msg.toString();
       notify();//唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用，发送数据给服务器，——>wait->等待被唤醒->返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(parm);
        //等待
        wait();
        //返回结果
        return result;
    }
    void setParm(String parm){
        this.parm=parm;
    }
}
