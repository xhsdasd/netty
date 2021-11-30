package NIO.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
    private ServerSocketChannel listenChannel;
    private Selector selector;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            //初始化参数
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            //注册事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen() {
        try {

            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "  上线啦");
                        }
                        if (key.isReadable()) {
                            //读数据
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readData(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端:" + msg);
                //转发信息
                sendMsgToOther(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "  下线啦");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void sendMsgToOther(String msg, SocketChannel channel) throws IOException {
        System.out.println("服务器转发信息中...");
        for (SelectionKey key : selector.keys()
        ) {
            SelectableChannel channel1 = key.channel();
            if (channel1 instanceof SocketChannel && channel1 != channel) {
                SocketChannel channel12 = (SocketChannel) channel1;
                channel12.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
