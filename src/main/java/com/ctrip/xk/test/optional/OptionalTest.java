package com.ctrip.xk.test.optional;

import java.util.Optional;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 20:13
 */
public class OptionalTest {
    public static void main(String[] args) {
        // Optional常用的静态构造方法有of(),empty()和ofNullable()方法
        Optional<String> optional = Optional.empty();
//        Optional<String> optional = Optional.ofNullable("hello");

        // 不推荐使用
        if (optional.isPresent()){
            System.out.println(optional.get());
        }

        // 强烈推荐使用，ifPresent使用的是Consumer接口，orElseGet方法使用的是Supplier接口
        optional.ifPresent(value -> System.out.println(value));
        System.out.println(optional.orElse("world"));
        System.out.println(optional.orElseGet(() -> "nihao"));
    }
}
