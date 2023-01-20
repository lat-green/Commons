package com.greentree.common.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

import org.junit.jupiter.api.Test;

public class AnalysisFunctionsTest {

	@Test
	void test1() {
		final var func = power(X, 2f / 3);
		System.out.println(func);
		System.out.println(func.derivative());
		System.out.println(func.antiderivative());
		System.out.println(func.arcfunc());
	}
	
}
