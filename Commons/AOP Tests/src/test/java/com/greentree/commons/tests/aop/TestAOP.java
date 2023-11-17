package com.greentree.commons.tests.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutowiredConfig(TestConfig.class)
public class TestAOP {

    @AutowiredTest
    void notNull(Person person) {
        assertNotNull(person.getName());
    }

    @AutowiredTest(tags = {"Anton"})
    void testTagAnton(Person person) {
        assertEquals(person.getName(), "Anton");
    }

}
