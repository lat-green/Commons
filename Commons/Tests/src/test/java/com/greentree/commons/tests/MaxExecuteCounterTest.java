package com.greentree.commons.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaxExecuteCounterTest {
	
	@Test
	void run_0() {
		try(final var b = new MaxExecuteCounter(1)) {
		}
	}
	
	@Test
	void run_1() {
		try(final var b = new MaxExecuteCounter(1)) {
			b.run();
		}
	}
	
	@Test
	void run_2() {
		Assertions.assertThrows(Throwable.class, ()-> {
			try(final var b = new MaxExecuteCounter(1)) {
				b.run();
				b.run();
			}
		});
	}
	
	@Test
	void run_3() {
		Assertions.assertThrows(Throwable.class, ()-> {
			try(final var b = new MaxExecuteCounter(1)) {
				b.run();
				b.run();
				b.run();
			}
		});
	}
	
}
