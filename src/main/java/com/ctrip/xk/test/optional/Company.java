package com.ctrip.xk.test.optional;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 20:38
 */
@Getter
@Setter
public class Company {
    private String name;
    private List<Employee> employees;
}
