package NIO.zerocopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7001));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        FileChannel fileChannel = new FileOutputStream("newNetty.pdf").getChannel();
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount=0;
            while (readCount>0){
                try {
                    //readCount = socketChannel.write(buffer);
                    readCount = fileChannel.write(buffer);
                } catch (IOException e) {
                    break;
                }
                //倒带
                buffer.rewind();
            }
            System.out.println("文件传输完成");

        }

    }
}
