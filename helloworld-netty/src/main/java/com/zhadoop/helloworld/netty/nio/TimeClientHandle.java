package com.zhadoop.helloworld.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try{
            //1.打开 SocketChannel
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            //2.设置SocketChannel 为非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeySet.iterator();
                SelectionKey key = null;
                //7.Selector 轮询就绪的 Key
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        //多路复用器关闭后，所有注册在上面的Channel 和 Pipe 等资源都会被自动注册并关闭，所以不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //判断是否连入成功
            SocketChannel sc = (SocketChannel) key.channel();
            //8.handleConnect()
            if(key.isConnectable()){
                //9.判断连接是否完成，完成执行步骤10
                if(sc.finishConnect()){
                    //10.向多路复用器注册读事件OP_READ
                    sc.register(selector,SelectionKey.OP_READ);
                    doWrite(sc);
                }else {
                    System.exit(1);
                }
            }

            if(key.isReadable()){
                //11.handlerRead() 异步读请求消息到ByteBuffer
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    //12.decode 请求消息
                    String body = new String(bytes,"UTF-8");
                    //13.打印
                    System.out.println("Now is : :" + body);
                    this.stop = true;
                }else if(readBytes < 0){
                    //对端链路关闭
                    key.channel();
                    sc.close();
                }else
                    //读到0字节，忽略
                    ;
            }
        }
    }

    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        //3.异步连接到服务端
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            //4.判断连接结果，如果连接成功，调到步骤10，否则执行步骤5
            socketChannel.register(selector, SelectionKey.OP_READ);
                doWrite(socketChannel);
            }else{
                //5.向Reactor 线程的多路复用器注册OP_CONNECT 事件
                socketChannel.register(selector,SelectionKey.OP_CONNECT);
            }
        }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){
            System.out.println("Send order 2 server succeed.");
        }

    }

}
