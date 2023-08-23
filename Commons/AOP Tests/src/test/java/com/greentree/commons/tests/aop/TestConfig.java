package com.greentree.commons.tests.aop;

public class TestConfig {

    @AutowiredProvider
    Person person1() {
        return Anton.INSTANCE;
    }

    @AutowiredProvider
    Person person2(Name name) {
        return new NamePerson(name);
    }

    @AutowiredProvider
    Name name1() {
        return Arseny.INSTANCE;
    }

}
