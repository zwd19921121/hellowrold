package com.zhadoop.helloworld.thread.create;

public class TestClass {

    public static void main(String[] args) {
        int num1 = 0;
        new Thread(new MyTask(num1)).start();
        int num2 = 0;
        new Thread(new MyTask(num2)).start();
        int num3 = 0;
        new Thread(new MyTask(num3)).start();
    }

    public static class MyTask implements Runnable{

        public int num;

        public MyTask(int num){
            this.num = num;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++){
                num++;
            }
            System.out.println(Thread.currentThread().getName() + ": " +num);
        }
    }

}
