package com.ctrip.xk.test.stream;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-28
 * Time: 0:32
 *
 * 输出结果：
 * .................
 * com.ctrip.xk.test.stream.LambdaTest@7db811c1
 * com.ctrip.xk.test.stream.LambdaTest$1@25f209f5
 *
 * 首先打印出...是因为main线程执行的比较快，所以先打印出了它。接着我们可以看到两个输出结果是不一样的，
 * 对于lambda表达式来说，其实它并没有开辟一个新的作用域，它的作用域与外层的类的作用域是一个作用域，
 *所以lambda表达式才会输出LambdaTest。所以lambda表达式其实并不是匿名内部类的一个语法糖或者说是另一种表现形式，
 * 其实二者根本就不是一回事，只是二者能完成同一工作。究其根源，二者根本不是一回事，匿名内部类的作用域其实是一个全新的作用域，
 * 而lambda的作用域其实就是外层类的作用域。
 */
public class LambdaTest {
    Runnable r1 = () -> System.out.println(this);

    // 这一步其实生成的是实现Runnable接口的一个匿名内部类，它的命名规则是外部类(public的类)$1（1代表的是该匿名内部类
    // 是第一个匿名内部类，如果是第二个则是$2形式的），所以可以看到第二个类是LambdaTest$1
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    };


    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();
        new Thread(lambdaTest.r1).start();

        System.out.println(".................");
        new Thread(lambdaTest.r2).start();
    }
}
