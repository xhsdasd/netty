package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建serversocketchannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建selector
        Selector selector = Selector.open();

        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置通道非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册到register,事件OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            if(selector.select(1000)==0){
                System.out.println("服务器等待了1s，无连接");
                continue;
            }
            //获取有事件的selectionkeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历selectionkeys
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成一个 socketchannel："+socketChannel.hashCode());
                    //channel设为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册读事件，同时给一个bytebuffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if(key.isReadable()){
                    SocketChannel readChannel =(SocketChannel) key.channel();
                    //获取buffer
                    ByteBuffer buffer =(ByteBuffer) key.attachment();

                    readChannel.read(buffer);
                    System.out.println("form 客户端:"+new String(buffer.array()));

                }
                //将key移除 防止重复处理
                keyIterator.remove();
            }
        }
    }
}
