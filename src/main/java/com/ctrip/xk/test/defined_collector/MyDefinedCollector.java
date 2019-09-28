package com.ctrip.xk.test.defined_collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by xuke
 * Description: 实现自定义的搜集器：需要实现Collector里面的5个方法:supplier/accumulator/combiner/finisher/characteristics
 * Date: 2019-08-22
 * Time: 0:07
 * <p>
 * 在介绍一下这几个方法的含义及调用时机之前，先说一下Collector<T, A, R>三个参数的含义：
 * 1. T表示的是流中待处理的元素类型；
 * 2. A表示的是中间结果的类型，即每次往A中添加T类型的参数；
 * 3. R表示的是返回结果的类型。
 * <p>
 * 1）.  Supplier<A> supplier()：用于生成中间容器A的，用来保存元素T；
 * 2）.  BiConsumer<A, T> accumulator();用于往容器A中不断的添加元素T；
 * 3）.  BinaryOperator<A> combiner();用于处理并行流使用的，因为在并行流中，每一个线程都有一个中间结果容器A，
 * 它是负责将所有的中间结果容器合并成一个容器A；
 * 4）. Function<A, R> finisher();这个操作是用来生成最终的结果R的。这里和下面的characteristics方法联系非常紧密，具体来说，
 *                                 在我们的例子中，由于中间结果和返回结果都是Set<T>类型的，并未对里面的元素做任何操作，
 *                                因此我们在不指定 Characteristics.IDENTITY_FINISH的情况下，底层实际上会调用我们的
 *                                finisher方法，然后调用apply()方法肯定是能执行成功的，但是我们如果指定上
 *                                 Characteristics.IDENTITY_FINISH的话，那么底层将直接进行强制类型转换，
 *                                 无需调用finisher方法将进行强制类型转换。（具体源码见ReferencePipeline的collect方法）
 *                                 部分源码： return collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)
 *                                            ? (R) container  : collector.finisher().apply(container);
 * 因此，我们在使用Characteristics.IDENTITY_FINISH的时候，要保证是能够直接进行强制类型转换的，不然将会直接报错（类型转换Exception）
 * 注意：虽然结果类型R和中间结果A都是一样的（假定都是Set<T>），但是其中的内容却是不一样的（例如删除了某个元素），
 * 我们应该去掉Characteristics.IDENTITY_FINISH，否则此时会执行强制类型转换，并报异常。让它去执行我们自己定义的finisher
 * <p>
 * 5）. characteristics方法中可以指定搜集器的类型有：CONCURRENT/UNORDERED/IDENTITY_FINISH
 * 注意：
 * a.) CONCURRENT是可以用来处理并发操作的，多个线程可以操作在同一个container上，经常和UNORDERED搭配使用。
 *     被该标识标志的时候且使用parallelStream()的话，将不会调用combiner()方法，因为此时只有一个中间容器，无需合并操作。
 *     但是，如果二者缺一的时候，都会调用combiner方法。并且我们只使用parallelStream()是不使用UNORDERED的话，
 *      会生成多个中间结果容器（可以在supplier方法中验证）。同时，应该切记，在使用parallelStream()和UNORDERED的时候，
 *     一定要避免在遍历容器的时候又对容器进行可变操作，不然会抛出ConcurrentModificationException.
 * b.)  UNORDERED必须保证集合是无序的，我们定义的List其实就是不允许的，但是set/map是可以的；
 * c.)  IDENTITY_FINISH是用来保证中间结果容器A和最后的返回结果R是一致的，才能使用，底层实现其实就是t -> t，即传进去啥返回的及时啥
 *     Collectors类里面的私有静态方法castingIdentity()基本也是这种实现。简单而言，被该标识标志的话，将不会再调用finisher方法，
 *     将直接进行强制类型转换。
 */
public class MyDefinedCollector<T> implements Collector<T, Set<T>, Set<T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        //return HashSet::new;// 等价于()-> new HashSet()，因为Supplier无需提供参数，但是返回一个值
        return () -> {
            System.out.println("............");
            return new HashSet<>();
        };

    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
         return Set<T>::add; // 等价于(set,item) -> set.add(item)
        /**
         * 下面的代码在配合parallelStream()和UNORDERED的时候，有可能会抛出ConcurrentModificationException，因为只有一个中间结果容器
         *         return (set, item) -> {
         *             System.out.println("accumulator" + set + " " + Thread.currentThread().getName());
         *             set.add(item);
         *         };
         */

    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        // 注意这里是有两个return的，第一个return 表示返回的是BinaryOperator类型，第二个参数表示返回的是泛型Set<T>
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Set<T>> finisher() {
        System.out.println("finisher invoked!");
        return Function.identity();// 等价于 t -> t;这是因为我们的中间结果类型A和最后的接过来行都是Set<T>
    }

    @Override
    public Set<Characteristics> characteristics() {
        // characteristics invoked!会被调用两次，一次是在源代码入口ReferencePipeline类的ReduceOps.makeRef()转到ReduceOps类
        // 的getOpFlags()会调用第一次，第二次调用是在ReferencePipeline类collect()方法的return语句 return collector.characteristics()
        // 调用第二次
        System.out.println("characteristics invoked!");
        // Characteristics.IDENTITY_FINISH不加的话，将直接进行把中间结果T强制类型转换为结果类型R
        //  加上的话会执行我们定义的finisher方法
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT));
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "hello", "hello world"));

        Set<String> set = list.parallelStream().collect(new MyDefinedCollector<>());
        System.out.println(set);


    }
}
