package com.ctrip.xk.test.defalut_method;

/**
 * Created by xuke
 * Description: MyClass2如果同时继承MyInterface1和MyInterface2，所以MyClass2里面含有两个接口的myMethod()方法
 *             而两个接口的地位是等价的，所以编译器到底取哪个接口的方法是无从得知的，所以会报错，所以解决的办法有：
 *             1）在这个类里面重写这个方法；
 *             2）换一种调用方式。(MyInterface2.super.myMethod();)
 * Date: 2019-08-12
 * Time: 23:48
 */
public class MyClass2 implements MyInterface1, MyInterface2 {
    @Override
    public void myMethod() {
       // System.out.println("MyClass2 invoked!");
        // 如果你就是想使用MyInterface2里面的myMethod方法
        MyInterface2.super.myMethod();
    }

    public static void main(String[] args) {
        MyClass2 myClass2 = new MyClass2();
        myClass2.myMethod();
    }
}
