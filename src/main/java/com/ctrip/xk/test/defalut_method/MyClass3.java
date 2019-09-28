package com.ctrip.xk.test.defalut_method;

/**
 * Created by xuke
 * Description: 区别于MyClass2的实现两个接口，我们在MyClass3中继承一个实现MyInterface1的类且实现MyInterface2接口
 *               这种情况下是不会报错的，因为java约定实现类的优先比接口的优先级更高一些，java认为是西纳雷更具体，接口的含义则是一种契约。
 * Date: 2019-08-12
 * Time: 23:57
 */
public class MyClass3 extends MyInterface1Impl implements MyInterface2 {
    public static void main(String[] args) {
        MyClass3 myClass3 = new MyClass3();
        myClass3.myMethod();
    }
}
