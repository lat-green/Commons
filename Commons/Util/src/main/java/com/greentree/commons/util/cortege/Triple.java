package com.greentree.commons.util.cortege;

import java.io.Serializable;
import java.util.Objects;

@Deprecated
public class Triple<V1, V2, V3> implements Serializable {

	private static final long serialVersionUID = 1L;
	public V1 v1;
	public V2 v2;
	public V3 v3;
	public Triple() {
	}
	public Triple(V1 v1, V2 v2, V3 v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
		if(!Objects.equals(v1, other.v1) || !Objects.equals(v2, other.v2) || !Objects.equals(v3, other.v3)) return false;
		return true;
	}
	@Override
	public int hashCode() {
		return Objects.hash(v1, v2, v3);
	}

	@Override
	public String toString() {
		return "Triple [" + v1 + ", " + v2 + ", " + v3 + "]";
	}



}
