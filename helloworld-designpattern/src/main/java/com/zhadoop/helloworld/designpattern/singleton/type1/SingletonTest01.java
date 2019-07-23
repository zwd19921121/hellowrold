package com.zhadoop.helloworld.designpattern.singleton.type1;

public class SingletonTest01 {

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

    //2. 本类内部创建对象实例
    private final static Singleton instance = new Singleton();

    //对外提供公有的静态方法，返回实例对象
    public static Singleton getInstance(){
        return instance;
    }
}

