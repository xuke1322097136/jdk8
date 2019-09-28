package com.ctrip.xk.test.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * Created by xuke
 * Description: 理解OfStream里面的tryAdvance的底层实现
 * Date: 2019-08-25
 * Time: 20:20
 *
 *      OfPrimitive可以视为OfInt/OfLong/OfDouble和Spliterator之间的桥梁，
 *      OfInt（Spliterator的内部接口）用于针对原生类型int构建的Spliterator。
 * 摘自OfInt的源码:
 * 打开Spliterator接口中的OfInt子接口，可以看到OfInt里面有两个tryAdvance方法：第一个是直接继承的OfPrimitive的接口，
 *                                       第二个tryAdvance方法则实现的Spliterator接口中的方法。
 *  第二个tryAdvance方法的底层实现：
 *    @Override
 *         default boolean tryAdvance(Consumer<? super Integer> action) {
 *             if (action instanceof IntConsumer) {
 *                 return tryAdvance((IntConsumer) action); // 传递引用，此时Consumer<Integer>就和IntConsumer基本等价了
 *                                                          // 只是一个是处理Integer类型，一个是处理int类型
 *             }
 *             else {
 *                 if (Tripwire.ENABLED)
 *                     Tripwire.trip(getClass(),
 *                                   "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
 *                 return tryAdvance((IntConsumer) action::accept);  // 传递行为，还有一点是：当我们ctrl按住accept的时候
 *                                                            // 它回跳到Consumer接口中，但是按住::将会跳转到IntConsumer中
 *             }
 *         }
 *      拉出来仔细分析：
 *          if (action instanceof IntConsumer) {
 *  *                 return tryAdvance((IntConsumer) action);
 *  *             }
 *
 *          // 一般情况下，我们进行这种判断的话都是有继承关系的，然后直接进行强制类型转换，而Consumer和IntConsumer直接明显是
 *  *         同一层次的关系，并不存在什么继承不继承的，所以为什么能直接进行强制类型转换呢？
 *  *      // 下面的例子里，可以看到i -> System.out.println(i)，它其实是满足Consumer<Integer>的定义，也是满足IntConsumer的
 *  *          定义的，所以方法传递进来的action直接就是满足IntConsumer类型的行为，我们可以直接将Consumer<Integer>转化为IntConsumer
 */
public class StreamTest13 {

    public static void test(Consumer<Integer> consumer){
        consumer.accept(100);
    }

    public static void main(String[] args) {
        Consumer<Integer> consumer = i -> System.out.println(i);
        IntConsumer intConsumer = i -> System.out.println(i);

        System.out.println(consumer instanceof Consumer);
        System.out.println(intConsumer instanceof IntConsumer);

        System.out.println(".........................");

        test(consumer); // 直接传递引用，面向对象
        test(consumer::accept);// 函数式传递，这里传递的是一种行为
        test(intConsumer::accept);// 函数式传递，这里传递的是一种行为


    }
}
