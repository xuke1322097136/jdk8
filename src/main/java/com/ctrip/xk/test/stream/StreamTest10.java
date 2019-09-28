package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuke
 * Description: 流的截断
 * Date: 2019-08-21
 * Time: 0:29
 */
public class StreamTest10 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "helloworld"));

        // 最后的输出结果为5
//        list.stream().mapToInt(item -> item.length()).filter(length -> length == 5).
//                findFirst().ifPresent(System.out::println);

        // 最后的输出结果为hello  5，而不是hello  5  world  5  helloworld
        // 错误理解：将三个单词都会传入到mapToInt方法中，并得到hello, return 5,接着输出world, return 5, 然后输出helloworld
        //          return 11。紧接着得到的 5 5 11 ，然后判断长度为5的第一个字符串，所以输出hello。
        // 正确理解：可以将流想象成一个容器，容器里存放的是对流里面的每一个元素的操作，如：mapToInt()里面的就是一种操作，filter()
        //           里面的而言是一种操作，等等。也就是说，流里面存放的是这些操作，真正开始执行的时候是将容器里面的这些操作逐个运用到
        //           每个元素上，并且对这些操作是串行化处理的，对于第一个元素运用完第一个操作之后紧接着运用第二个操作，进行第三个操作，
        //           而不是对第一个元素运用第一个操作，接着运用第二个元素进行第一个操作。而且操作里面存在着短路的逻辑规则。和逻辑与(&&)
        //           或者逻辑或(||)是一个意思，findFirst是一个短路操作，当找到第一个元素(hello)之后，后面的元素根本不会执行。
        // 如果将hello改成hello1，最后的结果是hello1, world , 5，因为hello1的长度不是5，接着在world字符串的时候短路。
        list.stream().mapToInt(item -> {
            int length = item.length();
            System.out.println(item);
            return length;
        }).filter(length -> length == 5).findFirst().ifPresent(System.out::println);

    }
}
