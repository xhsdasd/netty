package NIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {


    public static void main(String[] args) throws IOException {
        String str="hello, i am 小明";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
        //通过fileoutputstream 获取对应的channel
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //str放入bytebuffer
        byteBuffer.put(str.getBytes());
        //buffer flip反转
        byteBuffer.flip();
        //bytebuffer 写入channel
        channel.write(byteBuffer);
    }
}
