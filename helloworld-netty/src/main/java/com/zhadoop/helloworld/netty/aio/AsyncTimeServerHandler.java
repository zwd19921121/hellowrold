package com.zhadoop.helloworld.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable{
    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try{
            //创建异步服务端通道asynchronousServerSocketChannel
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            //绑定监听端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            //阻塞线程
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        //传递一个CompletionHandler 类型的handler 实例接收accept操作
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
