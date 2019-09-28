package com.ctrip.xk.test.consumer;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-09
 * Time: 0:14
 *
 * 注意：函数式接口里面只能含有一个抽象方法，但是如果重写的是父类中就含有的方法，那么我们该接口中仍然算作是只有一个抽象方法。
 * 源码里的解释：
 *  If an interface declares an abstract method overriding one of the
 *  * public methods of {@code java.lang.Object}, that also does
 *  * <em>not</em> count toward the interface's abstract method count
 *  * since any implementation of the interface will have an
 *  * implementation from {@code java.lang.Object} or elsewhere.
 */
@FunctionalInterface
interface MyInterface {
    void test();

    String toString(); // 该方法重写父类Object类中的方法，所以在该接口中仍然视为只有test()这一个抽象方法
}
public class Test2{
    public void  myTest(MyInterface myInterface){
        System.out.println(1);
        myInterface.test();
        System.out.println(2);
    }
    public static void main(String[] args) {
        Test2 test2 = new Test2();
        // 使用匿名内部类实现
        test2.myTest(new MyInterface() {
            @Override
            public void test() {
                System.out.println("myTest method invoked!");
            }
        });
        System.out.println("..................");
        // 使用lambda表达式实现
        // 为什么说
        test2.myTest(() -> System.out.println("myTest method invoked"));
        // 可见() -> System.out.println("myTest method invoked")就是MyInterface这个类型的对象！！！
        // 之所以要求函数式接口里面只能由一个抽象方法，
        // 是因为() -> System.out.println("myTest method invoked")这个对象里面的实现肯定是针对该抽象方法的
        // test()方法是不接受参数的，所以这个括号肯定不能省略
        MyInterface myInterface = () -> System.out.println("myTest method invoked");
        System.out.println(myInterface.getClass());
        System.out.println(myInterface.getClass().getSuperclass());
        System.out.println(myInterface.getClass().getInterfaces());
        System.out.println(myInterface.getClass().getInterfaces()[0]);
    }
}
