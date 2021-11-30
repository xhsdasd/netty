package NIO.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7001);

        String fileName = "nettylearn.pdf";


        //获取输入输出流
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[1024];
        long readCount = 0;
        long total = 0;

        //准备发送
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(buffer)) > 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送的总字节数 :" + total + " 耗时:" + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        fileInputStream.close();

    }
}
