package netty.dubborpc.provider;

import netty.dubborpc.publicinterface.HelloServer;

public class HelloServerImpl implements HelloServer {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端信息="+msg);
        //根据信息不同返回
        if (msg!=null){
            return "你好客户端,我已经收到你的信息["+msg+"]";
        }else {
            return "你好客户端,我已经收到你的信息";
        }
    }
}
