package com.zhadoop.helloworld.jvm.classloader;

public class MyTest5 {

    public static void main(String[] args) {
        System.out.println(MyChild5.b);
    }
}

interface MyParent5{
    public static int a = 5;
}

interface MyChild5 extends MyParent5{
    public static int b = 6;
}


