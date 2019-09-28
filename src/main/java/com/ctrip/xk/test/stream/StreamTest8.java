package com.ctrip.xk.test.stream;

import java.util.stream.IntStream;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-15
 * Time: 0:08
 */
public class StreamTest8 {
    public static void main(String[] args) {
        // 可以看到输出结果虽然是0和1，但是它一直都在执行（IDEA没有停下来）
        // 这是因为distinct是去重操作，iterate得到的结果是0，1，0，1.....
        // distinct方法在第二次得到0和1的时候他是不知道每一次iterate的结果都是0和1的，所以它一直在等待，一直在等待iterate的输出，相当于是一个无限流了
        // 其实将limit方法和distinct方法的交换一下就不会有问题了
       //   IntStream.iterate(0, i -> (i + 1) % 2).distinct().limit(6).forEach(System.out::println);
        IntStream.iterate(0, i -> (i + 1) % 2).limit(6).distinct().forEach(System.out::println);

    }
}
