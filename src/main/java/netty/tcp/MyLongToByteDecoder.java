package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyLongToByteDecoder extends ReplayingDecoder<Message> {
    /**
     *
     * @param channelHandlerContext 上下文
     * @param byteBuf 传入数据
     * @param list   解码后的消息
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyLongToByteDecoder decode 被调用");
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        Message message = new Message(len, bytes);
        list.add(message);
    }
}
