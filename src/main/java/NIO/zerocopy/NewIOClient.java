package NIO.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        String fileName = "nettylear.pdf";

        //得到文件channel、
        FileChannel channel = new FileInputStream(fileName).getChannel();
        System.out.println(channel.size());
        System.out.println(8 * 1024 * 1024);
        long startTime = System.currentTimeMillis();
//一次最多8m 否则需要分段传输
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送的总字节数：" + transferCount + " 耗时:" + (System.currentTimeMillis() - startTime));
        channel.close();

    }
}
