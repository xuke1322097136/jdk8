package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description: 熟悉流的各种方法
 * Date: 2019-08-14
 * Time: 22:47
 */
public class StreamTest5 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "helloworld"));
        list.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);// 下面的比较麻烦，在这用方法引用简化处理
//        list.stream().map(item -> item.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println(".......................");

        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        list1.stream().map(item -> item * item).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println(".......................");

        Stream<List<Integer>> stream1 = Stream.of(new ArrayList<>(Arrays.asList(1)),
                new ArrayList<>(Arrays.asList(2, 3, 4)), new ArrayList<>(Arrays.asList(5, 6)));
//        stream1.map(theList -> theList.stream()).flatMap(item -> item * item).
//                collect(Collectors.toList()).forEach(System.out::println);
        // 上面的代码是错误的，我们首先要进行扁平化处理，然后再对里面的每一个元素进行平方处理
        // flatMap和map的区别在于：map只是对里面的每一个元素进行处理，并不会对集合进行扁平化处理，即将多个集合合为一个
        //                         而flatMap则会将多个集合合并为一个集合
        stream1.flatMap(List::stream).map(item -> item * item).
                collect(Collectors.toList()).forEach(System.out::println);
        System.out.println(".......................");


    }
}
