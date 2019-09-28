package com.ctrip.xk.test.method_reference;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-12
 * Time: 23:07
 */
public class StudentComparator {

    public int compareByName(Student student1, Student student2){
        return student1.getName().compareToIgnoreCase(student2.getName());
    }

    public int compareByAge(Student student1, Student student2){
        return student1.getAge() - student2.getAge();
    }

}
