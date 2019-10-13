package com.zhadoop.helloworld.netty.bio1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if(args != null && args.length > 0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
            }
        }

        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("The time server i start in port : " + port);
            Socket socket = null;
            //首先创建一个时间服务器处理类的线程池
            TimeServerHandlerExecutePool sigleExecutor = new TimeServerHandlerExecutePool(50,10000); //创建I/O 任务线程池
            while (true){
                socket = server.accept();
                sigleExecutor.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server != null){
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
