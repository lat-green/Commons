package com.greentree.commons.tests.aop;

@AutowiredConfig(TestConfig.class)
public class Test1 {

    @AutowiredTest
    void test1(Person person) {
        System.out.println(person.getName());
    }

}
