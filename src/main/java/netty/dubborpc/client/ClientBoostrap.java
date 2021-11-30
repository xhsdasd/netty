package netty.dubborpc.client;

import netty.dubborpc.publicinterface.HelloServer;

public class ClientBoostrap {
    private static final String providerName = "HelloServer#";


    public static void main(String[] args) {
        //创建一个消费者
        NettyClient client = new NettyClient();
        //获取代理对象
        HelloServer bean = (HelloServer) client.getBean(HelloServer.class, providerName);
        String hello = bean.hello("你好 dubbo~");
        System.out.println("调用结果res=" + hello);
    }
}
