package com.greentree.common.util.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.greentree.commons.util.function.LambdaSaveFunction;

public class SaveFunctionTest {
	
	private boolean run_one;
	
	@BeforeEach
	void setup() {
		run_one = false;
	}
	
	@AfterEach
	void AfterEach() {
		assertTrue(run_one, "one run not run");
	}
	
	@Test
	void test1() {
		final var addExclamation = new LambdaSaveFunction<String, String>(x-> {
			run_one();
			return x + "!";
		});
		
		final var a = "hello";
		final var b = addExclamation.apply(a);
		final var c = addExclamation.apply(a);
		
		assertEquals(a + "!", b);
		assertEquals(b, c);
	}
	
	private void run_one() {
		assertFalse(run_one, "double run of one run");
		run_one = true;
	}
	
}
