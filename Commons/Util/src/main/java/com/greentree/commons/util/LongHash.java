package com.greentree.commons.util;

import java.io.Serializable;

public abstract class LongHash implements Serializable {
	private static final long serialVersionUID = 1L;

	public transient int hash;

	public static boolean equals(LongHash a, LongHash b) {
		if(a.hash == 0 || b.hash == 0) return true;
		return a.hash == b.hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		LongHash other = (LongHash) obj;
		return LongHash.equals(this, other);
	}

	@Override
	public final int hashCode() {
		if(hash == 0) hash = hashCode0();
		return hash;
	}

	protected abstract int hashCode0();

}
