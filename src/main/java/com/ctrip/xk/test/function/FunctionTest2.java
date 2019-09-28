package com.ctrip.xk.test.function;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 12:49
 */
public class FunctionTest2 {
    public static void main(String[] args) {
        FunctionTest2 test2 = new FunctionTest2();
        // 测试compose()方法和andThen()方法。在这传递的是一种行为（value -> 3 * value等），具体的操作由Function来完成
        // 具体执行什么操作/行为由用户指定，然后将具体的行为传递到下面的方法实现。
        // 由此可见，函数式编程提供了更高层次的抽象化
        System.out.println(test2.compute1(2, value -> 3 * value,  value -> value * value));// 2 * 2 = 4 -> 4 * 3 = 12
        System.out.println(test2.compute2(2, value -> 3 * value,  value -> value * value));// 3 * 2 = 6 -> 6 * 6 = 36

        System.out.println("....................................");

        // 测试BiFunction
        System.out.println(test2.compute3(3, 4, (value1, value2) -> value1 + value2));// 7
        System.out.println(test2.compute3(3, 4, (value1, value2) -> value1 - value2));// -1
        System.out.println(test2.compute3(3, 4, (value1, value2) -> value1 * value2));// 12
        System.out.println(test2.compute3(3, 4, (value1, value2) -> value1 / value2));// 0

        System.out.println("....................................");

        // 测试BiFunction的andThen()方法
        System.out.println(test2.compute4(3, 4, (value1, value2) -> value1 + value2, value -> value * value));// 49
    }

    // 第一个参数Integer为参数a的类型，第二个Integer的类型为funcion的执行结果返回类型
    // compose方法的执行顺序是先完成参数里面的function2的执行，接着执行当前对象function1的执行
    public int compute1(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
        return  function1.compose(function2).apply(a);
    }

    // andThen方法的执行顺序跟compose方法正好相反，它是先完成当前对象function1中的apply()方法的执行
    // 接着将执行结果传递给参数里面的function2，由它来调用，完成apply()方法的再次调用
    public int compute2(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
        return function1.andThen(function2).apply(a);
    }

    // 当我们需要传递的参数是2个参数的时候，我们使用的不再是Function，而是BiFunction
    public int compute3(int a, int b, BiFunction<Integer, Integer, Integer> biFunction){
        return biFunction.apply(a, b);
    }

    // 注意andThen方法里面的参数是一个Function类型的参数，而不是BiFunction类型的！
    // 原因是BiFunction虽然负责完成两个元素的操作，但是返回值却只有一个，所以当它完成了具体操作之后，
    // 再将结果传递给参数里面Function的apply执行，所以参数必须是Function类型类型的。
    //这也是为啥BiFunction里面没有compose()方法的原因，因为compose()方法的参数肯定是BiFunction类型的，而结果只返回一个值。
    public int compute4(int a, int b, BiFunction<Integer, Integer, Integer> biFunction,
                        Function<Integer, Integer> function){
        return biFunction.andThen(function).apply(a, b);
    }
}
