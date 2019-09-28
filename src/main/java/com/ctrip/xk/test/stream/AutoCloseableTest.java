package com.ctrip.xk.test.stream;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-24
 * Time: 14:05
 *
 * 分析源码的时候发现Stream继承BaseStream接口，而BaseStream接口又继承了AutoCloseable接口，它里面只有一个close()方法
 * 之前，我们用于关闭流，关闭数据库连接的流程基本都是：
 *
 *   1. 声明一个流（InputStream等）
 *   2. try 里面把流打开；
 *   3. 最后在finally里面把流显示地关闭
 *   具体的形式为：
 *   //  declare a stream;
 *   try{
 *        // open stream
 *   }catch(....){
 *
 *   }finally{
 *       // close stream
 *   }
 *
 *   而实现AutoCloseable接口的时候，将会自动调用close()方法，在使用完资源之后无需我们显示调用。
 *   Stream里面的源码：
 *   Most streams are backed by collections, arrays, or generating functions（生成器函数）, which require no
 *   special resource management.  (If a stream does require closing, it can be
 *   declared as a resource in a {@code try}-with-resources statement.)
 *
 *   Stream的声明：public interface Stream<T> extends BaseStream<T, Stream<T>>
 *   BaseStream的声明：public interface BaseStream<T, S extends BaseStream<T, S>>
 *       可以看到二者的区别，其实BaseStream里面的S其实就可以理解为Stream<T> ，正好二者的声明都是满足的。
 *       而且S其实可以理解为中间操作（intermediate operation）产生的一系列新的Stream对象。
 *
 */
public class AutoCloseableTest implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("close invoked!");
    }

    public void doSomething(){
        System.out.println("doSomething invoked!");
    }

    /**
     * 源码里的注释： This method is invoked automatically on objects managed by the @code try}-with-resources statement.
     * 我们的代码里的AutoCloseableTest test = new AutoCloseableTest()就是resources，
     * 在我们使用完资源test.doSomething();之后，close方法将会自动的被得到调用。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try(AutoCloseableTest test = new AutoCloseableTest()){
            test.doSomething();
        }
    }
}
