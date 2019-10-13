package com.zhadoop.helloworld.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
    private Selector selector;
    private ServerSocketChannel servChannel;
    private volatile  boolean stop;

    /**
     * 构造方法初始化
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            //创建多路复用器，参数设置
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            //2.绑定监听地址 InetSocketAddress
            servChannel.socket().bind(new InetSocketAddress(port),1024);
            //4.将ServerSocketChannel 注册到Selector ，监听
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start is start in port :" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                //5.Selector 轮询就绪的Key
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
            //6.处理新的客户端接入
            if(key.isAcceptable()){
                //Accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                //7.设置新建客户端连接的Socket 参数
                sc.configureBlocking(false);
                //8.向Selector 注册监听读操作 SelectionKey.OP_READ
                sc.register(selector,SelectionKey.OP_READ);
            }

            if(key.isReadable()){
                //read the data
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    //9. 异步读请求消息到ByteBuffer
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    //10.decode 请求消息
                    String body = new String(bytes,"UTF-8");
                    System.out.println("The time server receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equals(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    //11.ReactorThread: 异步写入ByteBuffer 到SocketChannel
                    doWrite(sc,currentTime);
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

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if(response != null && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
