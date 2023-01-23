package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.NullValue;

public class ValueEqualsTest {
	
	@Test
	void MutableValue_ConstValue() throws ClassNotFoundException, IOException {
		final var str = "Hello World";
		final var strm = new MutableValue<>(str);
		final var strc = ConstValue.newValue(str);
		assertEquals(strm, strc);
		assertEquals(strc, strm);
	}
	
	@Test
	void ConstValue_NullValue() throws ClassNotFoundException, IOException {
		final var strn = NullValue.instance();
		final var strc = ConstValue.newValue(null);
		assertEquals(strn, strc);
		assertEquals(strc, strn);
	}
	
}
