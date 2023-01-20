package com.greentree.common.math.analysis;

import com.greentree.common.math.Mathf;

public class AnalysisFunctions {

	public static final AnalysisFunction1 ZERO = value(0);
	public static final AnalysisFunction1 ONE = value(1);
	public static final AnalysisFunction1 E = value(Mathf.E);
	public static final AnalysisFunction1 X = XAnalysisFunction1.X;

	
	
	public static AnalysisFunction1 mult(float a, AnalysisFunction1 b) {
		return ConstMultFunction1.create(a, b);
	}
	public static AnalysisFunction1 mult(AnalysisFunction1 a, float b) {
		return mult(b, a);
	}
	public static AnalysisFunction1 mult(float a, float b) {
		return value(a * b);
	}
	public static AnalysisFunction1 inverse(AnalysisFunction1 a) {
		return InverseFunction1.create(a);
	}
	public static AnalysisFunction1 abs(AnalysisFunction1 a) {
		return AbsFunction1.create(a);
	}
	public static AnalysisFunction1 log(AnalysisFunction1 b, AnalysisFunction1 a) {
		return LogFunction1.create(a, b);
	}
	public static AnalysisFunction1 ln(AnalysisFunction1 func) {
		return LogFunction1.create(E, func);
	}
	public static AnalysisFunction1 inverse(float a) {
		return value(1f / a);
	}
	public static AnalysisFunction1 power(AnalysisFunction1 a, float b) {
		return ConstPowerFunction1.create(a, b);
	}
	public static AnalysisFunction1 mult(AnalysisFunction1 a, AnalysisFunction1 b) {
		return MultFunction1.create(a, b);
	}
	public static AnalysisFunction1 add(float a, AnalysisFunction1 b) {
		return add(b, a);
	}
	public static AnalysisFunction1 add(AnalysisFunction1 a, float b) {
		return ConstAddFunction1.create(a, b);
	}
	public static AnalysisFunction1 add(float a, float b) {
		return value(a + b);
	}
	public static AnalysisFunction1 add(AnalysisFunction1 a, AnalysisFunction1 b) {
		return AddFunction1.create(a, b);
	}
	public static ValueFunction1 value(float x) {
		return new ValueFunction1(x);
	}
	
}
