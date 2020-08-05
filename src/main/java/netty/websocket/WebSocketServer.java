package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
private int port;

    public WebSocketServer(int port) {
        this.port = port;
    }
    public void run()throws Exception{

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//服务器设置日记处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());//http编解码器
                            pipeline.addLast(new ChunkedWriteHandler());//是以块的方式写 所以需要块处理器
                            /*
                            http数据在传输过程中是分段的， HttpObjectAggregator可以将多个块聚合起来，
                            这也是为什么当浏览器发送大量的数据时，会发出多次http请求.
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /*
                            websocket是以帧的形式传递
                            websocketframe下有6个子类
                            浏览器请求时 ws://localhost:7001/hello 表示uri
                                 WebSocketServerProtocolHandler    核心功能将http协议升级websocket协议，保持长连接,通过101状态码
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new WebSocketHandlet());//自定义处理器
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args)throws Exception {
        new WebSocketServer(7001).run();
    }
}
