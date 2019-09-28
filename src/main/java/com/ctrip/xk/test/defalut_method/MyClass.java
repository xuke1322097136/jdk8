package com.ctrip.xk.test.defalut_method;

/**
 * Created by xuke
 * Description: 测试我们继承接口的话，那么我们可以直接使用接口中的default方法
 * Date: 2019-08-12
 * Time: 23:46
 *
 *
 */
public class MyClass implements MyInterface1 {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.myMethod();
    }
}
