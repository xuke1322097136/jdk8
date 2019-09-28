package com.ctrip.xk.test.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by xuke
 * Description: 几种创建Stream的方式
 * Date: 2019-08-13
 * Time: 23:29
 *
 *     流由三部分构成：
 *     1. 源；
 *     2. 零个或多个中间操作；
 *     3. 终止操作。
 *     下面一段话是Stream源码里面的一段话：
 ** <p>To perform a computation, stream
 *  * <a href="package-summary.html#StreamOps">operations</a> are composed into a
 *  * <em>stream pipeline</em>.  A stream pipeline consists of a source (which
 *  * might be an array, a collection, a generator function, an I/O channel,
 *  * etc), zero or more <em>intermediate operations</em> (which transform a
 *  * stream into another stream, such as Stream#filter(Predicate), and a
 *  * <em>terminal operation</em> (which produces a result or side-effect, such
 *  * as Stream#count() or forEach(Consumer).
 *
 *     流操作的分类：
 *     1. 惰性求值；（即，零个或多个中间操作）
 *     2. 及时求值。（即，终止操作）
 *
 *    Stream/IntStream/LongStream/DoubleStream之间是平行的关系，没有任何的继承关系，但是他们都继承自BaseStream
 *
 *    流的继承体系：
 *      流源和中间操作：                  BaseStream（顶层）
 *                                           /
 *                                     AbstractPipeline（定义了流的一些属性值）
 *                                         /
 *                                   ReferencePipeline （用于构建流源和中间操作的）
 *                                /             \             \
 *                        Head（源）     StatelessOp（无状态）   StatefulOp（有状态）
 *
 *      终止操作：                             TerminalOp
 *                                     /     \        \          \
 *                                FindOp   MatchOp  ForeachOp   ReduceOp
 *       Sink负责连接所有操作，类似于饮水槽的作用。
 */
public class StreamTest1 {
    public static void main(String[] args) {
        // 第一种形式，利用 可变参数
        Stream stream1 = Stream.of("hello", "world", "helloworld");

        // 第二种形式，利用数组
        String[] array = new String[]{"hello", "world", "helloworld"};
        Stream stream2 = Stream.of(array);
        Stream stream3 = Arrays.stream(array);

        // 第二种形式，利用集合
        List<String> list = new ArrayList<>(Arrays.asList(array));
        Stream stream4 = list.stream();
        /**
         * 源代码AbstractPipeline里面的这两个方法实际上是互斥的，即两个只会满足一个，要么两个都不满足。
         * 它们的作用都是为了获得sourceSpliterator
         *
         *   private Spliterator<?> sourceSpliterator;
         *  private Supplier<? extends Spliterator<?>> sourceSupplier;
         *
         *  另外：forEach方法是有两个实现的：一个是ReferencePipeline里面的实现，一个是ReferencePipeline的静态子类：Head
         *   只有sourceStageSpliterator（即没有中间操作，没有map的情况）调用forEach方法，且是在串行流的情况下，
         *   才会调用的是Head里面的forEach方法，其他情况全部用的是ReferencePipeline里面的forEach实现。
         *   接着调用的是ArrayListSpliterator里面的forEachRemaining方法。
         *   上面所述的逻辑是在ReferencePipeline里面的forEach里面的逻辑。
         *
         *   记住：ReferencePipeline代表的是流源和中间操作。流源其实描述的是数据的一种存储方式，因为流源本身就是由集合来构造出来的，
         *        而流源本身是通过Spliterator这样一个实例持有对原集合的一个引用，因为流操作的是集合，肯定拿到引用才能操作。所以，
         *        Head其实是用来构造Spliterator并存储数据的（Collection引用位于Spliterators里面的内部类IteratorSpliterator）。
         *        中间操作则是由StatelessOp和StatefulOp来完成的。由此可见，在没有遇到终止操作
         *        （TerminalOp接口的抽象实现类：FindOp/ForeachOp/MatchOp/ReduceOp）之前，其实都是一些元素的构建。
         *
         *   其中，这里的sourceStageSpliterator其实不是IteratorSpliterator（在Spliterators这个工具类中）
         *   而是ArrayList里面的静态内部类ArrayListSpliterator，我们试着打个断点可以看到，会在ArrayListSpliterator中停下来，
         *   这是因为ArrayList里面的spliterator方法重写了Collection里面的spliterator方法，
         *   也可以在ArrayList里面的forEachRemaining方法上打个断点测试一下
         */
        /**
         *  *  list.stream()会调用Head类来构造源结点（作为stream pipeline的头节点），Head是ReferencePipeline的子类，
         *  *               所以还是会调用AbstractPipeline（其实是一个双向链表）的第一个构造方法。接着map会生成StatelessOp的匿名内部类，
         *  *               而StatelessOp（和Head一个级别，只是它是处理中间节点的）是继承ReferencePipeline的，
         *                  而ReferencePipeline又是继承AbstractPipeline的，所以此时其实调用的是AbstractPipeline的，
         *                  第二个构造方法是往stream pipeline的构造下一个中间节点。注意这两个构造方法是不一样的。
         *  *
         *  *    StatelessOp抽象类是用来表示无状态（stateless）的中间操作的，并附加到pipeline中，可以看到它里面有一个重要的类Sink，
         *  *    A Sink instance is used to represent each stage of this pipeline（来自源码）,也就是说，Sink是用来表示某一个Stage的，
         *  *    我们的map/filter等等其实就是一个个的stage。
         *       Sink翻译过来又是水槽的意思，其实代表的就是流从源开始流，一直流到终止引水槽（TerminalSink类）。
         *        另外，有状态的中间操作叫StatefulOp，也是位于ReferencePipeline中。
         *  *    A sink may be in one of two states: an initial state（初始状态） and an active state（激活状态）.
         *  *  * It starts out in the initial state; the {@code begin()} method transitions
         *  *  * it to the active state, and the {@code end()} method transitions it back into
         *  *  * the initial state, where it can be re-used.  Data-accepting methods (such as
         *  *  * {@code accept()} are only valid in the active state.
         *  *
         *  *  上面所描述的意思其实就是：begin() -> accept() -> end()方法的执行顺序，我们在ReferencePipeline里面的map方法里的
         *  *  downstream.accept(mapper.apply(u));打上断点的话，它会跳到AbstractPipeline的copyInto方法中，我们可以看到sink的begin,
         *  *  然后我们的accept，最后是sink的end方法。因为ChainedReference（Sink的一个实现类）里面就实现了Sink的begin和end方法。
         *  The implementation of the {@code accept()} method must call the correct {@code accept()} method
         *  on the downstream {@code Sink}，这里的accept指的其实就是我们的System.out::println，即调用ArrayListSpliterator的
         *  forEachRemaining方法。
         *
         *  opWrapSink方法(具体的实现位于ReferencePipeline中)的意思其实就是：每一个元素都会经历完了所有的操作之后，才会轮到下一个元素，
         *  所以它的作用其实就是将这些中间操作和终止操作打包起来，合在一起在一个元素上使用。
         *  wrapSink（位于AbstractPipeline中）的方法的意思其实是将所有的Sink（即所有的中间操作即终止操作）包装起来，从右往左包装起来。
         *         类似于sink1 <- sink2 <- sink3的形式。
         *   然后AbstractPipeline中的copyInto方法实际上的作用是将我们之前包装好的sink运用到Spliterator中的每一个元素上：
         *   final <P_IN> void copyInto(Sink<P_IN> wrappedSink, Spliterator<P_IN> spliterator) {
         *         if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(getStreamAndOpFlags())) {
         *             wrappedSink.begin(spliterator.getExactSizeIfKnown());
         *             spliterator.forEachRemaining(wrappedSink); // 可以看到所有的sink运用到每个元素上
         *             wrappedSink.end();
         *         }
         *
         *  */
       stream4.map(item -> item + "_abc").forEach(System.out::println);
    }
}
