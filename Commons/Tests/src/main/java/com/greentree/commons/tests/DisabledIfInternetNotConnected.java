package com.greentree.commons.tests;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(DisabledIfInternetNotConnectedCondition.class)
public @interface DisabledIfInternetNotConnected {
	
}
