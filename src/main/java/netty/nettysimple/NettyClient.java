package netty.nettysimple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {

        //创建工作组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            //创建配置对象,设置参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)  //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道对象
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler()); //设置自己的handler
                        }
                    });

            System.out.println("客户端  .. is ok");

            //连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6667).sync();

            //监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }


    }
}
