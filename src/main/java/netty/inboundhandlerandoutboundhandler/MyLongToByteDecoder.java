package netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class MyLongToByteDecoder extends ByteToMessageDecoder {
    /**
     * @param channelHandlerContext 上下文
     * @param byteBuf               传入数据
     * @param list                  解码后的消息
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyLongToByteDecoder decode 被调用");

        //在调用readLong()方法前必须验证所输入的ByteBuf是否具有足够的数据
        if (byteBuf.readableBytes() >= 8) {
            //把数据处理后的数据转成Long 传递到下一个handler
            list.add(byteBuf.readLong());
        }
    }
}
