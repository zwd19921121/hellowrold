package com.zhadoop.helloworld.designpattern.singleton.type5;

public class SingletonTest05 {

    public static void main(String[] args) {
        //测试
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2);
    }
}

class Singleton{
    //1.构造私有化方法
    private Singleton(){}
    private static volatile Singleton instance;

    //对外提供公有的静态方法，加入双重代码检查
    public static synchronized Singleton getInstance(){
        //当时用该方法的时候创建
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

