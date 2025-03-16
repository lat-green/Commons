package com.greentree.commons.tests.aop;

import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@AutowiredConfig(TestConfig.class)
public class TestAOP {

    @Nested
    class NestedAOP {

        @AutowiredTest
        void shapeAreaMoreZero(Shape shape) {
            assertTrue(shape.getArea() > 0f);
        }

    }

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
