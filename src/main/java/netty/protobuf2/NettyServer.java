package netty.protobuf2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import netty.protobuf.StudentPOJO;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建bossgroup ，workergroup
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {


            //创建服务器启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)//配置boss,worker线程组
                    .channel(NioServerSocketChannel.class)//作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 16)//设置线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decode", new ProtobufDecoder(MyDataInfo.Mymessage.getDefaultInstance()));//加入protobuf解码器
                            pipeline.addLast(new NettyServerHandler());//自定义处理器
                        }
                    });

            System.out.println("服务器  ..... is ready");

            //绑定端口同步启动
            ChannelFuture channelFuture = serverBootstrap.bind(6667).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}