package com.ctrip.xk.test.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-08
 * Time: 23:49
 *     IDEA报错：lambda expressions are not supported at this language level '5'
 *     解决办法：https://blog.csdn.net/fenghuibian/article/details/52704057
 *
 *     如果一个接口（如Consumer接口等）被@FunctionalInterface注解标注，那么它就是函数式接口。
 *     函数式接口的定义：一个接口里面有且仅有一个抽象方法。注意：从JDK 8开始，接口里面也可以具体的实现方法了，但是该方法必须用default关键字标识。
 *                        同时，从java 8开始，也支持static的方法了，并且在接口里可以有相应的方法实现。
 *                       上面所说的抽象方法是自己本身定义的抽象方法，如果该接口重写了父类里面的方法，那还是算只有一个抽象方法。具体见示例Test2.java
 *    源码的定义：The type is an interface type and not an annotation type, enum, or class.
 *                 The annotated type satisfies the requirements of a functional interface.
 *     具体来说：1.如果一个接口只含有一个抽象方法（也可以包含default关键字标识的具体实现方法），那么这个接口就是函数式接口；
 *               2.如果我们在某一个接口上声明了@FunctionalInterface注解，那么编译器就会按照函数式接口的定义来要求该接口（即该接口只有一个抽象方法）；
 *               3. 如果一个接口只含有一个抽象方法，但我们并没有将该方法声明为@FunctionalInterface注解，但是编译器仍然会将它视为函数式接口。
 *
 *    注意1：如果某个接口是函数式接口，即必须满足函数式接口的定义，那么我们就可以使用lambda表达式编程，。
 *    Note that 2： instances of functional interfaces can be created with  lambda expressions, method references, or constructor references.
 *    注意 3：在函数作为一等公民的语言中，lambda表达式的类型都是函数，但是！在java中，lambda表达式是对象！！！其实是为了跟之前的jdk版本兼容
 *            而且lambda表达式必须依附于一种特别的对象类型，即函数式接口。
 */
public class Test1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));

        // 第一种迭代方式
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
        System.out.println("...........................");
        // 第二种迭代方式（当然还可以通过迭代器了）
        for (int number : list){
            System.out.println(number);
        }
        System.out.println("...........................");
        // 第三种迭代方式,forEach方法来自于Iterable接口，该接口里面在jdk8新增了两个default方法的具体实现（接口从8开始居然也有具体的方法实现了！）。
      // Consumer接口的中的唯一抽象方法只有一个参数且没有返回值。其实也跟消费者的意思差不多，把你的拿来消费，消费完了没有返回值
       list.forEach(new Consumer<Integer>() {
           @Override
           public void accept(Integer integer) {
               System.out.println(integer);
           }
       });
        System.out.println("...........................");
        // 第四种实现方式。为什么在这i不需要声明成Integer类型的？因为编译器会有 类型推断 的过程，它在执行这段代码的时候就知道了该参数类型为Integer
        // 其实我们也可以加上类型声明，即：（Integer i） -> System.out.println(i)
        list.forEach(i -> System.out.println(i));

        System.out.println("...........................");
        // 函数式接口的实例可以通过lambda表达式（lambda expressions），也可以通过方法引用（method references）或者构造函数（constructor references）来创建。
        // 其实你ctrl按住：：的时候，它自动就跳到Consumer接口中去了
        list.forEach(System.out::println);
    }
}
