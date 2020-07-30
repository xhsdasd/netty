package NIO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args)throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte) 'x');

        randomAccessFile.close();
        System.out.println("修改成功");

    }
}
