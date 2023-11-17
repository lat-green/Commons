package com.greentree.commons.tests.aop;

public class TestConfig {

    @AutowiredProvider(tags = "Anton")
    Person personAnton() {
        return Anton.INSTANCE;
    }

    @AutowiredProvider
    Person personCopyWithLastName(Person person) {
        return new NamePerson(person.getName() + " AAA");
    }

    @AutowiredProvider
    Person personFromName(Name name) {
        return new NamePerson(name);
    }

    @AutowiredProvider(tags = "Arseny")
    Name nameArseny() {
        return Arseny.INSTANCE;
    }

}
