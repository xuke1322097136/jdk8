package com.ctrip.xk.test.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 13:42
 */
public class PersonFunctionTest {
    public static void main(String[] args) {

        Person person1 = new Person("zhangsan", 20);
        Person person2 = new Person("lisi", 30);
        Person person3 = new Person("wangwu", 40);
        List<Person> list = new ArrayList<>(Arrays.asList(person1, person2, person3));

        PersonFunctionTest personTest = new PersonFunctionTest();
        List<Person> result1 = personTest.findPersonByUsername("zhangsan", list);
        result1.forEach(person -> System.out.println("名字：" + person.getUsername() + " 年龄：" + person.getAge()));

        System.out.println(".........................");

        List<Person> result2 = personTest.findPersonByAge(25, list);
        result2.forEach(person -> System.out.println("名字：" + person.getUsername() + " 年龄：" + person.getAge()));

        System.out.println(".........................");

        // statement lambda的写法
       List<Person> result3 =  personTest.findPersonByAge2(25, list, (ageOfPerson, personList) -> {
            return personList.stream().filter(person -> person.getAge() <= ageOfPerson).collect(Collectors.toList());
        });
       result3.forEach(person -> System.out.println("名字：" + person.getUsername() + " 年龄：" + person.getAge()));

    }

    public List<Person> findPersonByUsername(final String username, List<Person> persons) {
        return persons.stream().filter(person -> person.getUsername().equals(username)).
                collect(Collectors.toList());
    }

    // 换一种方法来实现，由于我们传进去的是两个参数，返回的结果是一个参数，所以我们可以使用BiFunction来实现
    public List<Person> findPersonByAge(int age, List<Person> persons) {
        // 在这注意statement lambda（如{return ....;}形式）和expression lambda（没有大括号和return关键字,即下面这种写法）两种形式
        BiFunction<Integer, List<Person>, List<Person>> biFunction = (ageOfPerson, personsList) ->
                personsList.stream().filter(person -> person.getAge() > ageOfPerson).collect(Collectors.toList());
        return biFunction.apply(age, persons);
    }

    public List<Person> findPersonByAge2(int age, List<Person> persons,
                                         BiFunction<Integer, List<Person>, List<Person>> biFunction) {
        return biFunction.apply(age, persons);
    }

}
