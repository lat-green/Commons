package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

public class ValueFunction1 implements AnalysisFunction1 {

	private final float value;

	public ValueFunction1(float x) {
		this.value = x;
	}

	@Override
	public boolean isBracketsNeeded() {
		return value < 0;
	}
	
	public float getValue() {
		return value;
	}
	
	@Override
	public float execute(float x) {
		return value;
	}

	@Override
	public AnalysisFunction1 derivative() {
		return value(0);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		return X;
	}

}
