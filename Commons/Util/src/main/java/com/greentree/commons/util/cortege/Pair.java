package com.greentree.commons.util.cortege;

import kotlin.Deprecated;
import kotlin.ReplaceWith;

import java.io.Serializable;
import java.util.Objects;

@Deprecated(message = "use kotlin.Pair", replaceWith = @ReplaceWith(expression = "kotlin.Pair", imports = {}))
public class Pair<T1, T2> implements Serializable {

    private static final long serialVersionUID = 1L;

    public T1 first;
    public T2 seconde;

    public Pair() {
    }

    public Pair(final Pair<T1, T2> pair) {
        this.first = pair.first;
        this.seconde = pair.seconde;
    }

    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.seconde = second;
    }

    public Pair<T2, T1> getInverse() {
        return new Pair<>(seconde, first);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, seconde);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pair other)) return false;
        return Objects.equals(first, other.first) && Objects.equals(seconde, other.seconde);
    }

    @Override
    public String toString() {
        return "{" + first + ", " + seconde + "}";
    }

}
