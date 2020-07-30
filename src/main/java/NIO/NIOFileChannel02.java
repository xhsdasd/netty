package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        File file = new File("d:\\file01.txt");
        //创建一个输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //创建channel
        FileChannel channel = fileInputStream.getChannel();
        //创建Bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //读取到buffer
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
    }
}
