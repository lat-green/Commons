package com.greentree.commons.tests.aop;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredProvider {

    String[] tags() default {};

}