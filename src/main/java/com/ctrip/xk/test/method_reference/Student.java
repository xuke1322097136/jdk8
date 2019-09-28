package com.ctrip.xk.test.method_reference;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-12
 * Time: 22:14
 */
@Getter
@Setter
public class Student {
    private String name;
    private int age;

    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    // 其实这种编码方式是不对的，因为静态方法在其他类里面都可以使用，既然定义在该类中，那么方法实现就应该使用到这些成员变量
    public static int compareByName(Student student1, Student student2){
        return student1.getName().compareToIgnoreCase(student2.getName());
    }

    public static int compareByAge(Student student1, Student student2){
        return student1.getAge() - student2.getAge();
    }

    // 相比于上面的实现形式，这两种方法实现比较好，用到了成员变量
    public int compareStudentByName(Student student){
        return this.getName().compareToIgnoreCase(student.getName());
    }

    public int compareStudentByAge(Student student){
        return this.getAge() - student.getAge();
    }
}
