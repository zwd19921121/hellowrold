package com.zhadoop.helloworld.designpattern.singleton.type6;

public class SingletonTest06 {

    public static void main(String[] args) {
        //测试
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2);
    }
}

class Singleton{
    private Singleton(){}
    //1.构造私有化方法
    private static  Singleton instance;

    //静态内部类，该类有一个静态属性
    private static class SingletonInstance{
        private static final Singleton INSTANCE = new Singleton();
    }

    //对外提供公有的静态方法
    public static  Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}

