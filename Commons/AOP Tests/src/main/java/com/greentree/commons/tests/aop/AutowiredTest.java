package com.greentree.commons.tests.aop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(AutowiredArgumentsProvider.class)
@ParameterizedTest
public @interface AutowiredTest {

    String[] tags() default {};

}