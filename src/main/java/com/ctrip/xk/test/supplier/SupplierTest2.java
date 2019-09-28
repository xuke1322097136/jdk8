package com.ctrip.xk.test.supplier;

import java.util.function.Supplier;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 19:37
 */
public class SupplierTest2 {
    public static void main(String[] args) {
        Supplier<Student> supplier = () -> new Student();
        System.out.println("name: " + supplier.get().getName() + " age: " + supplier.get().getAge() );
        System.out.println("................................");
        // 上面采用的是lambda表达式来完成，接下来用构造方法引用的形式构建
        Supplier<Student> supplier1 = Student::new;// 调用的是无参构造器
        System.out.println("name: " + supplier1.get().getName() + " age: " + supplier1.get().getAge() );
    }
}
