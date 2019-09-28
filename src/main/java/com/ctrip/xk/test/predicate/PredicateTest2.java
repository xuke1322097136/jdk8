package com.ctrip.xk.test.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 16:08
 *    体验函数式编程的好处（行为传递）：write less, do more（写的更少做的更多），因为我们只关注并传递具体的行为就可以了
 */
public class PredicateTest2 {

    public static void main(String[] args) {
        PredicateTest2 test2 = new PredicateTest2();
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        // test()方法的使用
        test2.conditionFilter(list, value -> value % 2 == 0);// 偶数
        System.out.println(".........................");
        test2.conditionFilter(list, value -> value % 2 != 0);// 奇数
        System.out.println(".........................");
        test2.conditionFilter(list, value -> value > 5);// 大于5
        System.out.println(".........................");
        test2.conditionFilter(list, value -> true);// 打印出所有数
        System.out.println(".........................");
        test2.conditionFilter(list, value -> false);// 过滤掉所有数

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");

        test2.conditionFilter2(list, value -> value > 5, value -> value % 2 == 0);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Predicate.isEqual其实和equals方法的作用是一样的
       // System.out.println(test2.isEquals("hello world").test("hello world"));// true
        System.out.println(test2.isEquals(new Date()).test(new Date()));// false

    }

    // 所有的方法都可以使用这个抽象的概念，具体的过滤规则由用户在使用的时候指定的！
    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate){
       for (Integer number : list){
           if (predicate.test(number)){
               System.out.println(number);
           }
       }
    }

    public void conditionFilter2(List<Integer> list, Predicate<Integer> predicate1, Predicate<Integer> predicate2){
        for (Integer number : list){
            // and方法的返回值就是Predicate，所以我们需要调用test方法将参数传进去
            // negate方法返回的是取反值
            if (predicate1.and(predicate2).test(number)) {
                System.out.println(number);
            }
        }
    }

    public Predicate<Date> isEquals(Object object){
         return Predicate.isEqual(object);
    }

}
