package com.ctrip.xk.test.function;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 19:41
 *
 *       BinaryOperator是BiFunction的子接口
 */
public class BinaryOperatorTest {
    public static void main(String[] args) {
        BinaryOperatorTest binaryOperatorTest = new BinaryOperatorTest();
        System.out.println(binaryOperatorTest.compute(2, 3, (a, b) -> a + b));
        System.out.println(binaryOperatorTest.compute(2, 3, (a, b) -> a - b));

        System.out.println("....................");

         // 自己定义比较规则，第一个比较规则是按照长度比较，第二个是按照首字母大小比较
        System.out.println(binaryOperatorTest.getShort("helloworld", "test", (a, b) -> a.length() - b.length()));
        System.out.println(binaryOperatorTest.getShort("helloworld", "test", (a, b) -> a.charAt(0) - b.charAt(0)));
    }

    // BinaryOperator是BiFunction的子接口，相对于BiFunction而言，BinaryOperator的三个参数都是一样的
    public int compute(Integer a, Integer b, BinaryOperator<Integer> binaryOperator){
        return binaryOperator.apply(a, b);
    }

    public String getShort(String a, String b, Comparator<String> comparator){
        return BinaryOperator.minBy(comparator).apply(a, b);
    }
}
