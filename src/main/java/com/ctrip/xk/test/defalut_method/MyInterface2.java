package com.ctrip.xk.test.defalut_method;

/**
 * Created by xuke
 * Description: MyInterface2和MyInterface1中的方法是一样的，只是实现不相同
 * Date: 2019-08-12
 * Time: 23:49
 */
public interface MyInterface2 {
    default void myMethod(){
        System.out.println("MyInterface2 invoked!");
    }
}
