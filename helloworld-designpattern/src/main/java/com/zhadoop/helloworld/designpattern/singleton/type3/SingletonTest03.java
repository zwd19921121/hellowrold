package com.zhadoop.helloworld.designpattern.singleton.type3;

public class SingletonTest03 {

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
    private static Singleton instance;

    //对外提供公有的静态方法，返回实例对象
    public static Singleton getInstance(){
        //当时用该方法的时候创建
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}

