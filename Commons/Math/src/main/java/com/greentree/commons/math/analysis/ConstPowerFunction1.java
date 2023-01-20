package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

import com.greentree.commons.math.Mathf;

public class ConstPowerFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 func;
	private final float power;
	
	private ConstPowerFunction1(AnalysisFunction1 func, float power) {
		this.func = func;
		this.power = power;
	}

	@Override
	public boolean isBracketsNeeded() {
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(func.isBracketsNeeded()) {
			builder.append("(");
			builder.append(func);
			builder.append(")");
		}else {
			builder.append(func);
		}
		builder.append("^");
		builder.append(power);
		return builder.toString();
	}


	public AnalysisFunction1 getFunc() {
		return func;
	}

	
	public float getPower() {
		return power;
	}

	@Override
	public float execute(float x) {
		return Mathf.pow(func.execute(x), power);
	}

	@Override
	public AnalysisFunction1 arcfunc() {
		return power(func.arcfunc(), 1f / power);
	}
	
	@Override
	public AnalysisFunction1 antiderivative() {
		if(func == X) {
			return mult(1f/(power + 1), power(func, power+1));
		}
		return AnalysisFunction1.super.antiderivative();
	}
	
	@Override
	public AnalysisFunction1 derivative() {
		return mult(power, mult(power(func, power - 1), func.derivative()));
	}

	public static AnalysisFunction1 create(AnalysisFunction1 a, float power) {
		if(Mathf.equals(power,  0))
			return value(1);
		if(Mathf.equals(power,  1))
			return a;
		if(Mathf.equals(power, -1))
			return inverse(a);
		if(a instanceof ConstPowerFunction1) {
			final var f = (ConstPowerFunction1)a;
			return power(f.func, f.power * power);
		}
		return new ConstPowerFunction1(a, power);
	}

}
