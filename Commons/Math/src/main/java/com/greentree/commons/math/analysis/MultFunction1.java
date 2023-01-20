package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

import java.util.Objects;

public class MultFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 a, b;

	private MultFunction1(AnalysisFunction1 a, AnalysisFunction1 b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public AnalysisFunction1 arcfunc() {
		return mult(a.arcfunc(), b.arcfunc());
	}
	
	@Override
	public AnalysisFunction1 antiderivative() {
		if(a == X) {
			return add(mult(X, b.derivative()), b);
		}
		if(b == X) {
			return add(mult(X, a.derivative()), a);
		}
		return AnalysisFunction1.super.antiderivative();
	}

	@Override
	public AnalysisFunction1 derivative() {
		return add(mult(a.derivative(), b), mult(a, b.derivative()));
	}

	@Override
	public float execute(float x) {
		return a.execute(x) * b.execute(x);
	}

	public AnalysisFunction1 getA() {
		return a;
	}

	public AnalysisFunction1 getB() {
		return b;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(a.isBracketsNeeded()) {
			builder.append("(");
			builder.append(a);
			builder.append(")");
		}else {
			builder.append(a);
		}
		builder.append(" * ");
		if(b.isBracketsNeeded()) {
			builder.append("(");
			builder.append(b);
			builder.append(")");
		}else {
			builder.append(b);
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		MultFunction1 other = (MultFunction1) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b);
	}

	public static AnalysisFunction1 create(AnalysisFunction1 a, AnalysisFunction1 b) {
		if(a.hashCode() > b.hashCode()) return create(b, a);
		if(b instanceof ValueFunction1) {
			final var v = ((ValueFunction1) b).getValue();
			return mult(v, a);
		}
		if(a instanceof ValueFunction1) {
			final var v = ((ValueFunction1) a).getValue();
			return mult(v, b);
		}
		if(a == b)
			return power(a, 2);
		if(b instanceof ConstPowerFunction1) {
			final var mf = (ConstPowerFunction1) b;
			if(mf.getFunc().equals(a))
				return power(mf.getFunc(), mf.getPower() + 1);
		}
		if(a instanceof ConstPowerFunction1) {
			final var mf = (ConstPowerFunction1) a;
			if(mf.getFunc().equals(a))
				return power(mf.getFunc(), mf.getPower() + 1);
		}
		if(a instanceof ConstPowerFunction1) {
			if(b instanceof ConstPowerFunction1) {
				final var mfa = (ConstPowerFunction1) a;
				final var mfb = (ConstPowerFunction1) b;
				if(mfa.getFunc().equals(mfb.getFunc()))
					return power(mfa.getFunc(), mfa.getPower() + mfb.getPower());
			}
		}
		return new MultFunction1(a, b);
	}



}
