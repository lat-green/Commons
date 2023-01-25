package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.Sources;
import com.greentree.commons.tests.ExecuteCounter;

public class MergeTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "A";
	
	@Test
	void closeTest() {
		try(final var t1 = new ExecuteCounter(1);final var t2 = new ExecuteCounter(1)) {
			final var v1 = new CloseEventValue<>(new MutableSource<>(TEXT1), t1);
			final var v2 = new CloseEventValue<>(new MutableSource<>(TEXT2), t2);
			
			final var m = Sources.merge(v1, v2);
			
			m.openProvider().close();
		}
	}
	
	@Test
	void test_NEW() {
		final var v1 = new MutableSource<>(TEXT1);
		final var v2 = new MutableSource<>(TEXT2);
		
		final var m = Sources.merge(v1, v2);
		
		try(final var p = m.openProvider()) {
			assertFalse(p.isChenge());
			v1.set(TEXT1);
			assertFalse(p.isChenge());
			v1.set(null);
			assertTrue(p.isChenge());
			p.get();
		}
	}
	
	
}
