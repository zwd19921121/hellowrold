package com.zhadoop.helloworld.jvm.classloader;

public class MyTest4 {
    public static void main(String[] args) {
//        MyParent4 myParent4 = new MyParent4();
//        System.out.println("======");
//        MyParent4 myParent41 = new MyParent4();

        MyParent4[] myParent4s = new MyParent4[1];
        System.out.println(myParent4s.getClass());

        MyParent4[][] myParent4s1 = new MyParent4[1][1];
        System.out.println(myParent4s1.getClass());

        System.out.println(myParent4s.getClass().getSuperclass());
        System.out.println(myParent4s1.getClass().getSuperclass());
        System.out.println("=====");
        int[] ints = new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());
    }
}

class MyParent4{
    static{
        System.out.println("MyParent4 static block");
    }
}
