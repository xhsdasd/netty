package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGathering  {
    public static void main(String[] args) throws IOException {

        //使用serversocketchannel 和 socketchanner网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);
        //telnet等待连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;

        //循环读取
        while(true){

            int readByte=0;
            while (readByte<messageLength){
                long read = socketChannel.read(byteBuffers);
                readByte+=read;
                System.out.println("readByte="+readByte);
                Arrays.asList(byteBuffers).stream().map(buffer->"position="+buffer.position()+",limit="+buffer.limit()).forEach(System.out::println);

            }
            Arrays.asList(byteBuffers).forEach(buffer->buffer.flip());

            //显示数据
            long byteWrite=0;
            while (byteWrite<messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWrite+=write;
            }
            //清空buffer
            Arrays.asList(byteBuffers).forEach(buffer->buffer.clear());

            System.out.println("readByte="+readByte+"  writeByte="+byteWrite+"  messagelength="+messageLength);
        }
    }
}
