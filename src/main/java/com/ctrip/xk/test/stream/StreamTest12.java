package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description: 测试BaseStream里面的onClose方法，并验证doc文档里的描述
 * Date: 2019-08-24
 * Time: 19:39
 * <p>
 * onClose(Runnable closeHandler)源码描述;
 * *    验证点1：Returns an equivalent stream with an additional close handler.  Close
 *              handlers are run when the #close() method is called on the stream, and
 *              are executed in the order they were added.
 *      验证点2：All close handlers are run, even if earlier close handlers throw exceptions.
 *      验证点3：If any close handler throws an exception, the first
 *              exception thrown will be relayed to the caller of {@code close()}, with
 *              any remaining exceptions added to that exception as suppressed exceptions
 *       验证点4：(unless one of the remaining exceptions is the same exception as the
 *                first exception, since an exception cannot suppress itself.)  May
 *                return itself.
 */
public class StreamTest12 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "hello world"));

        /**
         *  验证点0：不调用close方法的执行结果:只会输出list里面的内容，onClose()方法里面的内容并不会输出
         *          原因其实就是我们没有调用close() method 。还有onClose方法里面是一个Runnable接口，
         *          里面的run()方法无需传递参数也无需返回值。
         *
         */
        list.stream().onClose(() -> System.out.println("aaa")).
                onClose(() -> System.out.println("bbb")).
                forEach(System.out::println);

        System.out.println("...........................");
        /**
         * 验证点1：当调用了close()方法（隐式调用，详情见AutoCloseableTest类里面）的时候，onClose()方法里面的内容会不会输出。
         *          我们使用try-with-resources实现的时候，便会自动调用close()方法。
         */
        try (Stream<String> stream = list.stream()) {
            stream.onClose(() -> System.out.println("aaa")).
                    onClose(() -> System.out.println("bbb")).
                    forEach(System.out::println);
        }

        System.out.println("...........................");
        /**
         * 验证点2：验证close handlers的执行顺序和当某一个hanlder抛出异常的时候，后面的handler还会不会执行.
         * 可以看到输出结果：hello  world  hello world aaa  bbb
         *                  Exception in thread "main" java.lang.NullPointerException: first exception
         *                  Suppressed: java.lang.NullPointerException: second exception
         *  可见当抛出一个exception的时候，后面的还是会正常执行的，且只有第一exception会抛出给调用者，后面的exception
         *  则会以supressed的形式压缩起来。原因其实也很简单，因为每一个close handler都是互相独立的，
         *  不应该出现相互干扰的情况发生。
         */
//        try (Stream<String> stream = list.stream()) {
//            stream.onClose(() -> {
//                System.out.println("aaa");
//                throw new NullPointerException("first exception");
//            }).onClose(() -> {
//                System.out.println("bbb");
//                throw new NullPointerException("second exception");
//            }).forEach(System.out::println);
//        }

        System.out.println("...........................");
        /**
         * 验证点3：验证源码里面的unless里面的内容，即我们抛出的是同一个异常的情况下，最后的异常输出情况。
         * 可以看到输出结果：hello  world  hello world aaa  bbb
         *                 Exception in thread "main" java.lang.NullPointerException: my defined exception
         *                 此时不会有什么所谓的压缩异常出现，因为都是同一个异常，没有什么压缩不压缩的。
         *
         */
        NullPointerException nullPointerException = new NullPointerException("my defined exception");
        try (Stream<String> stream = list.stream()) {
            stream.onClose(() -> {
                System.out.println("aaa");
                throw nullPointerException;
            }).onClose(() -> {
                System.out.println("bbb");
                throw nullPointerException;
            }).forEach(System.out::println);
        }

    }
}
