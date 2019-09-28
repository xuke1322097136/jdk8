package com.ctrip.xk.test.stream;

import java.util.IntSummaryStatistics;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description: 熟悉Stream的其他几个方法，并理解  行为传递  的方式
 * Date: 2019-08-14
 * Time: 23:14
 */
public class StreamTest6 {
    public static void main(String[] args) {
        Stream stream = Stream.generate(UUID.randomUUID()::toString);
        //findFirst方法返回结果是Optional[58506ede-4f47-485a-a498-0f98bec679c8]，之所以返回一个Optional对象是因为有可能流里面不存在元素
//        System.out.println(stream.findFirst());
        //   System.out.println(stream.findFirst().get()); // 这种编码方式是会被IDEA警告的，因为这里对get方法调用有可能会报错(NoSuchElementException)。
        //  Optional的正确写法，千万别什么isPresent，然后get()，不然就跟之前的null写法没区别了
        stream.findFirst().ifPresent(System.out::println);
        System.out.println("...................");

        // iterate方法的第一个参数表示是初始值，然后第二个参数是UnaryOperator类型的，其实是Function<T, T>类型的，即参数和返回值都是T
        // iterate返回的是一个无限流，所以要用limit方法限制返回结果个数。另外，它的原理是f(f(f...f(seed)))，它会对初始值不断地进行迭代
        Stream.iterate(1, i -> i + 2).limit(6).forEach(System.out::println);
        System.out.println("...................");

        Stream<Integer> stream1 = Stream.iterate(1, item -> item + 2).limit(6);
        // 这一行采用的的映射方法是mapToInt，这里是为了避免装箱和拆箱带来的性能损耗，因为map方法返回的是包装类型Integer，而我们要用的还是int类型的数
        // 这一行的意思是找到大于2的数，将他们分别乘以2倍，然后过滤掉前2个元素，然后取出前2个元素并将它们求和
        // 对于sum最后返回的值是整数int，而如果是 max/min方法，返回的则是InStream对象，因为有可能流里面没有元素，而sum如果没有元素返回0即可
        System.out.println(stream1.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).sum());
        // 如果要求最大值，则换成下面这种形式
//        stream1.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).max().ifPresent(System.out::println);
        System.out.println("...................");

        Stream<Integer> stream2 = Stream.iterate(1, item -> item + 2).limit(6);
        // IntSummaryStatistics 这个类里面是设置了默认值的，所以其实也不会越界
        IntSummaryStatistics intSummaryStatistics = stream2.filter(item -> item > 200).
                mapToInt(item -> item * 2).skip(2).limit(2).summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());// max = Integer.MIN_VALUE;
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMin()); // min = Integer.MAX_VALUE;

        System.out.println("...................");

        // 下面这三行代码会出现：java.lang.IllegalStateException: stream has already been operated upon or closed
        // 这是因为stream2被重复使用了，第二行的输出用stream2生成了一个新的Stream，第三行的输出又用stream2生成了一个新的Stream
        // 换成下面的几行代码就不会了
//        System.out.println(stream2);
//        System.out.println(stream2.filter(item -> item > 2));
//        System.out.println(stream2.distinct());
        Stream<Integer> stream3 = Stream.iterate(1, item -> item + 2).limit(6);
        System.out.println(stream3);
        Stream<Integer> stream4 = stream3.filter(item -> item > 2);
        System.out.println(stream4);
        Stream<Integer> stream5 = stream4.distinct();
        System.out.println(stream5);

    }
}
