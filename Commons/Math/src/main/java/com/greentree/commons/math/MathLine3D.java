package com.greentree.commons.math;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;

import java.util.Objects;

public class MathLine3D {

    private AbstractVector3f vector;
    private final AbstractVector3f point;

    public MathLine3D(AbstractVector3f point, AbstractVector3f vector) {
        this.point = new Vector3f(point);
        this.vector = new Vector3f(vector);
        if (this.vector.x() < 0)
            this.vector = this.vector.times(-1);
        this.vector.normalize(1f);
    }

    public AbstractVector3f p1() {
        return vector;
    }

    public AbstractVector3f p2() {
        return vector.plus(point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, vector);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MathLine3D other = (MathLine3D) obj;
        return Objects.equals(point, other.point) && Objects.equals(vector, other.vector);
    }

    @Override
    public String toString() {
        return "MathLine3D [vector=" + vector + ", point=" + point + "]";
    }

}
