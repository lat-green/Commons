package com.greentree.common.math.analysis;

import com.greentree.common.math.Mathf;
import static com.greentree.common.math.analysis.AnalysisFunctions.*;

import java.util.Objects;

public class AbsFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 func;

	public AbsFunction1(AnalysisFunction1 func) {
		this.func = func;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(func);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		AbsFunction1 other = (AbsFunction1) obj;
		return Objects.equals(func, other.func);
	}

	@Override
	public float execute(float x) {
		return Mathf.abs(func.execute(x));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("|");
		builder.append(func);
		builder.append("|");
		return builder.toString();
	}

	public static AnalysisFunction1 create(AnalysisFunction1 func) {
		if(func instanceof ValueFunction1) {
			final var v = ((ValueFunction1) func).getValue();
			if(v < 0)
				return value(-v);
			return func;
		}
		return new AbsFunction1(func);
	}

}
