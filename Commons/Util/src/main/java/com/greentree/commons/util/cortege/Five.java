package com.greentree.commons.util.cortege;

import java.util.Objects;

public class Five<V1, V2, V3, V4, V5> {

	public V1 v1;
	public V2 v2;
	public V3 v3;
	public V4 v4;
	public V5 v5;

	public Five() {

	}

	public Five(Triple<V1, V2, V3> v123, V4 v4, V5 v5) {
		this(v123.v1, v123.v2, v123.v3, v4, v5);
	}

	public Five(V1 v1, V2 v2, V3 v3, V4 v4, V5 v5) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		this.v5 = v5;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Five other = (Five) obj;
		if(!Objects.equals(v1, other.v1) || !Objects.equals(v2, other.v2) || !Objects.equals(v3, other.v3) || !Objects.equals(v4, other.v4)) return false;
		if(!Objects.equals(v5, other.v5)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(v1, v2, v3, v4, v5);
	}

	@Override
	public String toString() {
		return "File [" + v1 + ", " + v2 + ", " + v3 + ", " + v4 + ", " + v5 + "]";
	}



}
