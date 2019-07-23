package com.zhadoop.helloworld.designpattern.singleton.type2;

public class SingletonTest02 {

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


    private final static Singleton instance;

    static {
        //2. 在静态代码块中创建
        instance = new Singleton();
    }

    //对外提供公有的静态方法，返回实例对象
    public static Singleton getInstance(){
        return instance;
    }
}

