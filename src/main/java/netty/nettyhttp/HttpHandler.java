package netty.nettyhttp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    //读取客户端信息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断是否request请求
        if(msg instanceof HttpRequest){
            HttpRequest request = (HttpRequest) msg;
            //过滤
            if(request.uri().equals("/favicon.ico")){
                System.out.println("ico请求，不做处理");
                return;
            }

        }
        //回复信息 给浏览器(http协议)
        ByteBuf content= Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

        //构建一个httpreponse
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");//设置响应类型
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());//响应长度
        ctx.writeAndFlush(response);

    }


}
