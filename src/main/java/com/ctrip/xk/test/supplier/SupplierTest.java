package com.ctrip.xk.test.supplier;

import jdk.management.resource.ResourceId;

import java.util.function.Supplier;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-11
 * Time: 18:58
 */
public class SupplierTest {
    public static void main(String[] args) {
        // Supplier表示不接收参数同时返回一个结果（适用于类似工厂模式那种场景）
         Supplier<String> supplier = () -> "hello world";
        System.out.println(supplier.get());
    }
}
