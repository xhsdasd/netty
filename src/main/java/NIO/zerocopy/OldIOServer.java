package NIO.zerocopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(7001));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readCount = 0;
        //FileOutputStream fileOutputStream = new FileOutputStream("oldNewFile.pdf");

        while (true) {
            SocketChannel channel = socketChannel.accept();
            while (readCount != -1) {
                try {
                    readCount = channel.read(buffer);

                    //fileOutputStream.write(buffer.array());

                    //读完倒带
                    buffer.rewind();
                } catch (IOException e) {
                    break;
                }

            }

            System.out.println("文件传输完成");
            //fileOutputStream.close();
        }
    }
}
