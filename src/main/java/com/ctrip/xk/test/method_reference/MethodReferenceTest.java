package com.ctrip.xk.test.method_reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-12
 * Time: 22:54
 *
 * 方法引用其实lambda表达式的一种语法糖（本身并没有提供任何新的功能，只是使用了一种更简洁或者更加方便的方式以供我们使用），
 * 我们可以将方法引用视为【函数指针】，function pointer，
 * 举例：下面的Student::compareByAge就可以看作是一个函数指针，具体的lambda实现其实就是具体实现：
 * students.sort((studentParm1, studentParm2) -> Student.compareByAge(studentParm1, studentParm2));
 *
 *   方法引用的四种形式：
 *    1.）类名：：静态方法；
 *    2.）引用名（对象名）：：实例方法；
 *    3.）类名：：实例方法；
 *    4.）构造方法引用：类名：：new
 *
 *    方法引用的使用场景：lambda表达式恰好只有行代码且这行代码恰好调用的方法就是已经存在的，
 *                        这个时候我们可以使用用方法引用替代lambda表达式，其他的情况都使用lambda表达式
 */
public class MethodReferenceTest {

    public String getString(Supplier<String> supplier){
        return supplier.get() + "test";
    }

    public  String getString2(String str, Function<String, String> function){
        return function.apply(str);
    }

    public static void main(String[] args) {
        Student student1 = new Student("zhangsan", 11);
        Student student2 = new Student("lisi", 33);
        Student student3 = new Student("wangwu", 22);
        Student student4 = new Student("zhaoliu", 44);

        List<Student> students = new ArrayList<>(Arrays.asList(student1, student2, student3, student4));
        // 注意：从jdk8开始，List接口也开始有自己的sort方法了，即我们可以不再使用Collections.sort()方法了
        // 1.）类名：：静态方法；
//        students.sort((studentParm1, studentParm2) -> Student.compareByAge(studentParm1, studentParm2)); // <=>students.sort(Student::compareByAge);
//        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));
//        System.out.println("................................................");
        students.sort((studentParm1, studentParm2) -> Student.compareByName(studentParm1, studentParm2)); // <=>students.sort(Student::compareByName);
        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // 为了防止利用上面排好序的结果，所以我们在这将上面的代码引掉
        //  2.）引用名（对象名）：：实例方法；
//        StudentComparator studentComparator = new StudentComparator();
//        students.sort((studentParm1, studentParm2) -> studentComparator.compareByAge(studentParm1, studentParm2));// <=>students.sort(studentComparator::compareByAge);
//        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));
//        System.out.println("................................................");
//        students.sort((studentParm1, studentParm2) -> studentComparator.compareByName(studentParm1, studentParm2));//<=>students.sort(studentComparator::compareByName);
//        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // 3.）类名：：实例方法；
        // 这种形式是比较难理解的，这种实现形式其实是将lambda形式的第一个参数(studentParm1)视为调用该方法的实例，
        // lambda表达式剩下的参数(studentParm2)将作为该方法的参数（student）传递进去
        students.sort(Student::compareStudentByName);
        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));
        System.out.println("................................................");
        students.sort(Student::compareStudentByAge);
        students.forEach(item -> System.out.println("name: " + item.getName() + " age: " + item.getAge()));

        // 3.）类名：：实例方法；
        // 可以借助这个例子理解一下上面的实现原理
       List<String> cites = new ArrayList<>(Arrays.asList("sichuan", "qingdao", "tianjin", "beijing"));
        Collections.sort(cites, (city1, city2) -> city1.compareToIgnoreCase(city2));
        cites.forEach(System.out::println);
        System.out.println("................................................");
        Collections.sort(cites, String::compareToIgnoreCase);//简便方法：cites.sort(String::compareToIgnoreCase);
        cites.forEach(System.out::println);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        MethodReferenceTest referenceTest = new MethodReferenceTest();
        System.out.println(referenceTest.getString(String::new));// 调用的是无参构造器
        System.out.println(referenceTest.getString2("hello", String::new));// 调用有参构造器，返回的是String类型的对象，符合Function泛型中的第二个泛型类型
    }
}
