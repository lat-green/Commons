package com.greentree.commons.tests.aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredArgument {

    String[] tags();

}