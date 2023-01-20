package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

public class InverseFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 func;

	private InverseFunction1(AnalysisFunction1 func) {
		this.func = func;
	}

	public AnalysisFunction1 getFunc() {
		return func;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("1/");
		if(func.isBracketsNeeded()) {
			builder.append("(");
			builder.append(func);
			builder.append(")");
		}else
			builder.append(func);
		return builder.toString();
	}

	@Override
	public float execute(float x) {
		return 1f / func.execute(x);
	}

	@Override
	public AnalysisFunction1 derivative() {
		return mult(-1, mult(func.derivative(), power(func, -2)));
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		if(func == X) {
			return ln(abs(func));
		}
		return AnalysisFunction1.super.antiderivative();
	}

	public static AnalysisFunction1 create(AnalysisFunction1 a) {
		if(a instanceof ConstPowerFunction1) {
			final var p = (ConstPowerFunction1) a;
			return power(p.getFunc(), -p.getPower());
		}
		if(a instanceof ValueFunction1) {
			final var v = ((ValueFunction1) a).getValue();
			return value(1f / v);
		}
		if(a instanceof MultFunction1) {
			final var mf = (MultFunction1) a;
			return mult(inverse(mf.getA()), inverse(mf.getB()));
		}
		if(a instanceof ConstMultFunction1) {
			final var mf = (ConstMultFunction1) a;
			return mult(inverse(mf.getConst()), inverse(mf.getFunc()));
		}
		return new InverseFunction1(a);
	}
	
	@Override
	public AnalysisFunction1 arcfunc() {
		return inverse(func.arcfunc());
	}
	
}
