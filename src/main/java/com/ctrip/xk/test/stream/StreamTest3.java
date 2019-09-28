package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuke
 * Description:  将数字的两倍加起来
 * Date: 2019-08-13
 * Time: 23:29
 *
 * 集合更加关注的是数据的存储，而流更加关注的是数据的计算。
 * Stream里面源码解释：
 * * Collections and streams, while bearing some superficial similarities,
 *  * have different goals.  Collections are primarily concerned with the efficient
 *  * management of, and access to, their elements.  By contrast, streams do not
 *  * provide a means to directly access or manipulate their elements, and are
 *  * instead concerned with declaratively describing their source and the
 *  * computational operations which will be performed in aggregate on that source.
 */
public class StreamTest3 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 4, 5, 6));

        // 这句话的意思是：将list转换为流对象之后，然后将每个数字变为原来的两倍，然后将各个数字加起来
        // reduce的第一个参数的意思是起始值为0
        System.out.println(list.stream().map(item -> 2 * item).reduce(0, Integer::sum));
    }
}
