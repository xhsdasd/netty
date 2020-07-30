package BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
            //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建socket
        ServerSocket socket = new ServerSocket(6666);

        System.out.println("服务器启动啦");

        while(true){
            System.out.println("线程信息id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName());
            //监听
            System.out.println("等待连接");
            final Socket accept = socket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程
          executorService.execute(()->{handle(accept);});


        }





    }
    public static void handle(Socket socket){
        try {
            System.out.println("线程信息id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (inputStream.read(bytes)!=-1){
                System.out.println("线程信息id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName());
                System.out.println("read....");
                System.out.println(new String(bytes,0,bytes.length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
