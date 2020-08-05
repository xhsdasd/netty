package netty.nettygroupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//全局事件处理器是一个单例
    private SimpleDateFormat dfs=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //最早执行方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]用户:"+channel.remoteAddress()+"加入聊天啦 "+dfs.format(new Date()));
        channelGroup.add(channel);
    }
//离线
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]用户:"+channel.remoteAddress()+"离开啦 "+dfs.format(new Date()));
    }

    //上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]用户:"+channel.remoteAddress()+"上线啦 "+dfs.format(new Date()));
    }
//离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]用户:"+channel.remoteAddress()+"离线啦 "+dfs.format(new Date()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch->{
            if(ch!=channel){
                ch.writeAndFlush("[服务器]用户"+channel.remoteAddress()+"说:"+s+" "+dfs.format(new Date()));
            }else{
                channel.writeAndFlush("[服务器]你发送了消息:"+s+" "+new Date());
            }

        });
    }
//异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
     ctx.close();
    }
}
