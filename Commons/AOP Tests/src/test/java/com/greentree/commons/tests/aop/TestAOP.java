package com.greentree.commons.tests.aop;

import static org.junit.jupiter.api.Assertions.*;

@AutowiredConfig(TestConfig.class)
public class TestAOP {

    @AutowiredTest
    void notNull(Person person) {
        assertNotNull(person.getName());
    }

    @AutowiredTest
    void testTagAnton(@AutowiredArgument(tags = {"Anton"}) Person person) {
        assertEquals(person.getName(), "Anton");
    }

    @AutowiredTest
    void doubleArgumentNotNull(Person person, Shape shape) {
        assertNotNull(person);
        assertNotNull(person.getName());
        assertNotNull(shape);
    }

    @AutowiredTest
    void shapeAreaMoreZero(Shape shape) {
        assertTrue(shape.getArea() > 0f);
    }

}
