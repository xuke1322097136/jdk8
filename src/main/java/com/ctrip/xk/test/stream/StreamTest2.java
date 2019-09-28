package com.ctrip.xk.test.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description:  三种输出结果都是一样的
 * Date: 2019-08-13
 * Time: 23:29
 */
public class StreamTest2 {
    public static void main(String[] args) {

        IntStream.of(new int[]{4, 5, 6, 7}).forEach(System.out::println);

        System.out.println("...................");

        IntStream.range(4, 8).forEach(System.out::println);// 含左不含右
        System.out.println("...................");
        IntStream.rangeClosed(4, 7).forEach(System.out::println);// 含左也含右

    }
}
