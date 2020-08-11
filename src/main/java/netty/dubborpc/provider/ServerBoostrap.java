package netty.dubborpc.provider;

public class ServerBoostrap {
    public static void main(String[] args) {
        NettyServer.start("127.0.0.1",9001);
    }
}
