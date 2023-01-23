package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.merge.Group2;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.Values;
import com.greentree.commons.tests.ExecuteCounter;

public class MergeTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "A";
	
	@Test
	void closeTest() {
		try(final var t1 = new ExecuteCounter(1);final var t2 = new ExecuteCounter(1)) {
			final var v1 = new CloseEventValue<>(new MutableValue<>(TEXT2), t1);
			final var v2 = new CloseEventValue<>(new MutableValue<>(TEXT2), t2);
			
			final var m = Values.merge(v1, v2);
			
			m.close();
		}
	}
	
	@Test
	void test_NEW() {
		final var v1 = new MutableValue<>(TEXT2);
		final var v2 = new MutableValue<>(TEXT2);
		
		final var m = Values.merge(v1, v2);
		
		assertFalse(m.isConst());
		assertFalse(m.isNull());
		
		assertEquals(m.get(), new Group2<>(TEXT1, TEXT2));
		
		try(final var count = new ExecuteCounter(1)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(null);
			}
		}
		
		assertEquals(m.get(), new Group2<>(null, TEXT2));
		
		try(final var count = new ExecuteCounter(1)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(TEXT1);
			}
		}
		
		assertEquals(m.get(), new Group2<>(TEXT1, TEXT2));
		
		try(final var count = new ExecuteCounter(2)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(TEXT1);
				v2.set(TEXT2);
			}
		}
		
	}
	
	
}
