package netty.hearthat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartHatServerHandler extends ChannelInboundHandlerAdapter{
    //处理心跳超时
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String type=null;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    type="读空闲";
                    break;
                case ALL_IDLE:
                    type="读写空闲";
                    break;
                case WRITER_IDLE:
                    type="写空闲";
                    break;
            }
            System.out.println("超时时间 类型"+type);

        }
    }
}
