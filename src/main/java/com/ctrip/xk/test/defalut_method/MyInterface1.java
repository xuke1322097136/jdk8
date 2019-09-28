package com.ctrip.xk.test.defalut_method;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-12
 * Time: 23:45
 *
 * 引入默认方法的目的其实很大程度上是为了保证向后兼容。
 */
public interface MyInterface1 {
    default void myMethod(){
        System.out.println("MyInterface1 invoked!");
    }
}
