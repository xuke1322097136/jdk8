package com.ctrip.xk.test.optional;

import java.util.*;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 20:38
 */
public class OptionalTest2 {
    public static void main(String[] args) {
        Employee employee1 = new Employee();
        employee1.setName("zhangsan");

        Employee employee2 = new Employee();
        employee2.setName("lisi");

        Company company = new Company();
        company.setName("company");
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2));
       // company.setEmployees(employees);

        // 传统的实现方式
        List<Employee> result = company.getEmployees();
        if(null != result){
            System.out.println(result);
        }else {
            System.out.println(new ArrayList<>());
        }
        System.out.println(".......................");

        // 使用Optional和函数式编程来实现
        Optional<Company> optional = Optional.ofNullable(company);// 构造出容器对象
        // map函数里面接受的是Function类型的对象，即传进去一个值，返回一个值
        System.out.println(optional.map(theCompany -> theCompany.getEmployees()).
                orElse(Collections.EMPTY_LIST));
    }
}
