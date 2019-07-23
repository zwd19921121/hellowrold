package com.zhadoop.helloworld.designpattern.singleton.type7;

public class SingletonTest07 {

    public static void main(String[] args) {
        //测试
        Singleton instance1 = Singleton.INSTANCE;
        Singleton instance2 = Singleton.INSTANCE;
        System.out.println(instance1 == instance2);
    }
}

enum  Singleton{
    INSTANCE;
    public void sayOK(){
        System.out.println("say ok");
    }
}

