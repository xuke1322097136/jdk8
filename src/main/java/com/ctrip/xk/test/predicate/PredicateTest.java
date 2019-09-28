package com.ctrip.xk.test.predicate;

import java.util.function.Predicate;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 14:26
 */
public class PredicateTest {
    public static void main(String[] args) {

        Predicate<String> predicate = s -> s.length() > 5;
        System.out.println(predicate.test("hello1"));
    }
}
