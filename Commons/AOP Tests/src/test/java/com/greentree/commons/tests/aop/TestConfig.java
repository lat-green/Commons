package com.greentree.commons.tests.aop;

public class TestConfig {

    @AutowiredProvider(tags = "Anton")
    Person personAnton() {
        return Anton.INSTANCE;
    }

    @AutowiredProvider
    Shape newRect() {
        return new Rect(15f);
    }

    @AutowiredProvider
    Shape newTriangle() {
        return new Triangle(15f);
    }

    //    @AutowiredProvider
//    Person personCopyWithLastName(Person person) {
//        return new NamePerson(person.getName() + " AAA");
//    }
//
    @AutowiredProvider
    Person personFromName(Name name) {
        return new NamePerson(name);
    }

    @AutowiredProvider(tags = "Arseny")
    Name nameArseny() {
        return Arseny.INSTANCE;
    }

}
