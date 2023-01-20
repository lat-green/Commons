package com.greentree.commons.util.cortege;

/**
 * @author Arseny Latyshev
 *
 */
public class UnOrentetPair<T> extends Pair<T, T> {
	private static final long serialVersionUID = 1L;

	public UnOrentetPair() {
	}

	public UnOrentetPair(Pair<T, T> pair) {
		super(pair.first, pair.seconde);
	}

	public UnOrentetPair(T first, T seconde) {
		super(first, seconde);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof UnOrentetPair)) return false;
		UnOrentetPair other = (UnOrentetPair) obj;
		if(first.equals(other.first)) return seconde.equals(other.seconde);
		else
			if(seconde.equals(other.seconde)) return first.equals(other.first);
			else
				if(first.equals(other.seconde)) return seconde.equals(other.first);
				else
					if(seconde.equals(other.first)) return first.equals(other.seconde);
					else
						return false;
	}

	@Override
	public UnOrentetPair<T> getInverse() {
		return new UnOrentetPair<>(this);
	}

	public UnOrentetPair<T> reset(T e1, T e2) {
		first = e1;
		seconde = e2;
		return this;
	}

}
