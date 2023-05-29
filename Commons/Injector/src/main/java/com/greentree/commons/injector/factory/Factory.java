package com.greentree.commons.injector.factory;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Factory{
	
	Class<? extends ObjectFactory<?>> value();
	
}
