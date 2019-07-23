package com.zhadoop.helloworld.designpattern.simplefactory.pizzastore.order;

import com.zhadoop.helloworld.designpattern.simplefactory.pizzastore.pizza.CheesePizza;
import com.zhadoop.helloworld.designpattern.simplefactory.pizzastore.pizza.GreekPizza;
import com.zhadoop.helloworld.designpattern.simplefactory.pizzastore.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
    //构造器
    public OrderPizza(){
        Pizza pizza = null;
        String orderType; //订购披萨的类型
        do{
            orderType = gettype();
            if(orderType.equals("greek")){
                pizza = new GreekPizza();
                pizza.setName(" 希腊披萨");
            }else if(orderType.equals("cheese")){
                pizza = new CheesePizza();
                pizza.setName(" 奶酪披萨");
            }else {
                break;
            }
            //输出pizza 制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }while (true);
    }

    private String gettype(){
        try{
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();
            return str;
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

}
