package com.ctrip.xk.test.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description: 熟悉一下collect方法的原理
 * Date: 2019-08-13
 * Time: 23:47
 */
public class StreamTest4 {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("hello", "world", "helloworld");

        // 通过lambda的形式表示，toArray方法接受的是IntFunction接口，里面的apply()方法传入的是一个整数，最后得到的是一个字符串数组
        String[] array = stream.toArray(length -> new String[length]);
        Arrays.asList(array).forEach(System.out::println);

        System.out.println(".....................");

        Stream<String> stream1 = Stream.of("hello", "world", "helloworld");
        String[] strings = stream1.toArray(String[]::new);
        Arrays.asList(strings).forEach(System.out::println);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // 可以看看collect方法的源码，里面的实现其实是由下面的实现方式实现的
        Stream<String> stream2 = Stream.of("hello", "world", "helloworld");
        List<String> list = stream2.collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println(".................");

        // 第一个参数：实现的是Supplier接口，最终返回的list就是() -> new ArrayList<String>()创建的list
        // 第二个参数：实现的BiConsumer接口，传入了两个参数，没有返回值，最终将流里面的数据添加到一个theList临时列表中
        // 第三个参数：实现的BiConsumer接口，最终将所有的临时list添加到一个list中，并将结果返回给第一个参数创建的ArrayList
        Stream<String> stream3 = Stream.of("hello", "world", "helloworld");
        ArrayList<String> list3 = stream3.collect(() -> new ArrayList<String>(),
                (theList, item) -> theList.add(item), (list1, list2) -> list1.addAll(list2));
        list3.forEach(System.out::println);

        System.out.println(".................");
        Stream<String> stream4 = Stream.of("hello", "world", "helloworld");
        // Collectors.toList()底层使用的是ArrayList，在这我们使用LinkedList来实现方法引用，使用的是类名：：实例方法
        // 即，lambda表达式的第一个参数是调用该实例方法的对象，lambda表达式剩下的参数都是传递给实例方法的参数
        LinkedList<Object> list4 = stream4.collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        list4.forEach(System.out::println);
        System.out.println(".................");
        // 换一种方式定制 LinkedList，其实这种方法可以转换成任意集合
        Stream<String> stream5 = Stream.of("hello", "world", "helloworld");
        LinkedList<String> list5 = stream5.collect(Collectors.toCollection(LinkedList::new));
        list5.forEach(System.out::println);

        System.out.println(".................");
        Stream<String> stream6 = Stream.of("hello", "world", "helloworld");
        // 默认按照TreeSet里面的equals方法来实现排序
        TreeSet<String> treeSet = stream6.collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSet.getClass());
        treeSet.forEach(System.out::println);

        System.out.println(".................");

        // 测试Collectors.joining()的拼接方法，它的返回值就是String
        Stream<String> stream7 = Stream.of("hello", "world", "helloworld");
        String s = stream7.collect(Collectors.joining());
        System.out.println(s);

    }
}
