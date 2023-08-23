package com.greentree.commons.tests.aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredConfig {

    Class<?> value();

}