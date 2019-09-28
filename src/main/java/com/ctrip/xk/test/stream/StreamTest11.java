package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuke
 * Description: flatMap和map的区别！理解flatMap很重要！
 * Date: 2019-08-21
 * Time: 0:51
 */
public class StreamTest11 {
    public static void main(String[] args) {
        List<String> list  = new ArrayList<>(Arrays.asList("hello world", "world hello",
                "hello welcome", "hello world welcome"));

        // 在这可以看到map方法的返回值是Stream<String[]>，最后的输出结果是所有的字符串，显然不符合我们的预期
//        List<String[]> result = list.stream().map(item -> item.split(" ")).distinct().
//                collect(Collectors.toList());
//        // 里面的每一个item都是一个数组
//        result.forEach(item -> Arrays.asList(item).forEach(System.out::println));

        // Stream<string[]> -> Stream<String>
        // 在这，每一个字符串都会得到一个Stream<String>，最后flatMap调用会将四个Stream<String>合并成一个Stream<String>
        // 并将里面的内容合并到一起。Arrays::stream方法接受的是数组（T[]），最后返回的是Stream<T>。
        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).
                distinct().collect(Collectors.toList()).forEach(System.out::println);
    }
}
