package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuke
 * Description: 流的执行时机：只有遇到终止操作的时候，中间操作(map方法)才会真正取执行
 * Date: 2019-08-14
 * Time: 23:55
 * <p>
 * Stream类里面的源码解释：
 * Streams are lazy; computation on the source data is only performed when the
 * terminal operation is initiated, and source elements are consumed only
 * as needed.（这句话描述的需要的时候其实就是遇到终止操作的时候）
 */
public class StreamTest7 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "helloworld"));
        // 将每一个字符串的首字母转化为大写
        // 记住：这里最终的循环只循环了一次，对于Stream而言，它里面含有一个容器，里面存放着我们添加的各个操作，
        //  一旦遇到及早求值的时候，它将存放在容器中的操作按照我们施加的顺序逐个应用到集合中的每一个元素上。
        list.stream().map(item -> item.substring(0, 1).toUpperCase() + item.substring(1)).forEach(System.out::println);

        System.out.println("..................");
        // 下面的例子表示如果没有终止操作（及时求值）的话，我们得到的最终结果是啥输出结果也没有，
        // 只有加上.forEach(System.out::println)，才能看到输出结果。这是因为：stream的中间操作都是lazy的，都是惰性求值的
        // 只有遇到终止操作的时候，中间操作(map方法)才会真正取执行
        List<String> list2 = new ArrayList<>(Arrays.asList("hello", "world", "helloworld"));
        list2.stream().map(item -> {
            String result = item.substring(0, 1).toUpperCase() + item.substring(1);
            System.out.println("test");
            return result;
        }).forEach(System.out::println);
    }
}
