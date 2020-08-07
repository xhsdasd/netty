package netty.tcp;

import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncoder extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode被调用");
        byteBuf.writeInt(message.getLen());
        byteBuf.writeBytes(message.getContent());
    }
}
