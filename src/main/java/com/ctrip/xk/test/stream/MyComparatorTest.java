package com.ctrip.xk.test.stream;

import java.util.*;

/**
 * Created by xuke
 * Description: 比较器的使用方式，Jdk8为Comparator接口提供了大量新的方法，用于比较
 * Date: 2019-09-07
 * Time: 14:00
 */
public class MyComparatorTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("nihao", "hello", "world", "welcome"));

        // 按照字符串的长度进行升序排序
        Collections.sort(list, (item1, item2) -> item1.length() - item2.length());
        System.out.println(list);
        System.out.println("....................");

        // 按照字符串的降序进行排序：使用Jdk8比较器里面提供的静态方法和默认方法来代替lambda表达式的比较形式
        Collections.sort(list, Comparator.comparingInt(String::length).reversed());
        System.out.println(list);
        System.out.println("....................");

        // 当我们对item不加上String类型指定的时候，调用length()方法就会报错。这是因为idea会把item识别成Object类型
        // 这里就涉及到lambda的类型推断，在这就是无法进行类型推断的场景，因此需要我们自己指定参数类型.
        // 为什么在这编译器无法推断不出参数类型呢，明明list里面放置的都是String类型的数据啊？
        // reversed()返回的是Comparator<T>类型的，而item -> item.length()距离上下文（sort方法的第二个参数）比较远，
        // 因为它相当于是套在reversed()里面一层了，所以它感知不到list里面的参数到底是什么类型的，
        // 所以编译器把它看成是Object类型的，当我们将reversed()删除掉之后，item的类型就推断出来了
        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()).reversed());
        System.out.println(list);
        System.out.println("....................");

        // 直接使用Jdk8新增加的List里面的排序方法
        list.sort(Comparator.comparingInt(String::length));
        System.out.println(list);
        System.out.println("....................");

        // 先按照长度，然后按照不区分大小写的升序排序
        // thenComparing的作用：第一个比较器的比较结果是0，才会被调用thenComparing，否则，第一个比较器就是能得出比较结果的话，
        //                      thenComparing是不会发挥作用的。String.CASE_INSENSITIVE_ORDER返回的是Comparator<T>类型
        Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));
        /**
         * 和上面的作用是一样的(thenComparing有重载的两个方法：一种是Function的参数，还有一种还是Comparator<T>的参数)：
         * 方法一：Collections.sort(list, Comparator.comparingInt(String::length).
         *                    thenComparing(String::compareToIgnoreCase));
         * 方法二： Collections.sort(list, Comparator.comparingInt(String::length).
         *                 thenComparing(Comparator.comparing(String::toLowerCase)));
         */
        System.out.println(list);
        System.out.println("....................");

        // comparing()函数的第二个参数又是一个Comparator类型，我们将长度一样的字符串再按照逆序排一下
        Collections.sort(list, Comparator.comparingInt(String::length).
                         thenComparing(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder())));
        System.out.println(list);
        System.out.println("....................");

        // 再添加一个逆序操作
        Collections.sort(list, Comparator.comparingInt(String::length).reversed().
                thenComparing(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder())));
        System.out.println(list);
        System.out.println("....................");

        Collections.sort(list, Comparator.comparingInt(String::length).reversed().
                thenComparing(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder())).
                thenComparing(Comparator.reverseOrder()));
        // 这个结果其实和上面是一样的。因为第二个thenComparing是不起作用的，因为上一步的三个字符串的内容是不一样的
        System.out.println(list);

    }
}
