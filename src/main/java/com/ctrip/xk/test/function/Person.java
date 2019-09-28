package com.ctrip.xk.test.function;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 13:42
 */
@Data
public class Person {
    private String username;
    private int age;

     public Person(String username, int age){
         this.username = username;
         this.age = age;
     }

}
