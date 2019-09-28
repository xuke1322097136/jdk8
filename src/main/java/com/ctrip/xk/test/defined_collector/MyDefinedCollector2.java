package com.ctrip.xk.test.defined_collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-22
 * Time: 1:07
 *
 * 收集器的实现只有两种形式：
 * 1. 通过new CollectorImpl来实现的（CollectorImpl是Collectors里面的静态内部类）
 * 2. 通过reducing()来实现（其实还是通过new CollectorImpl来实现的）
 *
 *   可以花时间好好看看Collectors类里面的静态方法，尤其是比较常用的那些：toCollection/toList/joining/mapping/collectingAndThen
 *   counting/minBy/maxBy/summingInt/averagingInt/groupingBy/groupingByConcurrent/partitioningBy的具体实现方式，尤其是groupingBy
 *   和partitioningBy的实现形式，理解起来其实有点点难度。（可以参见张龙老师的视频）
 *
 */
public class MyDefinedCollector2<T> implements Collector<T, Set<T>, Map<T, T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
        return (set, item) -> set.add(item);
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked!");

        // 将中间类型T转换为结果类型R
        return set -> {
            Map<T, T> map = new HashMap<>();
            set.stream().forEach(item -> map.put(item, item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
        // 在这加上Characteristics.IDENTITY_FINISH就会报错，因为Set是没办法强制类型转换为Map的
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "hello", "a", "b", "c"));
        Set<String> set = new HashSet<>();
        set.addAll(list);

        System.out.println(set);

        Map<String, String> result = set.stream().collect(new MyDefinedCollector2<>());
        System.out.println(result);


    }
}
