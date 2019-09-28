package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuke
 * Description: 串行流和并行流
 * Date: 2019-08-21
 * Time: 0:19
 */
public class StreamTest9 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(5000);
        for (int i = 0; i < 5000; i++){
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");

        // nanoTime()相比较currentTimeMillis()方法精度更高
        long startTime = System.nanoTime();

//        list.stream().sorted().count();// 结果为147017501
        list.parallelStream().sorted().count();// 结果为115922001

        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toNanos(endTime - startTime);
        System.out.println("排序所花时间：" + millis);

    }
}
