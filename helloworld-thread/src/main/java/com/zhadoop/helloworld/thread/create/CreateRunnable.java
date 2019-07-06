package com.zhadoop.helloworld.thread.create;

public class CreateRunnable implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println("i:" + i);
        }
    }
}
