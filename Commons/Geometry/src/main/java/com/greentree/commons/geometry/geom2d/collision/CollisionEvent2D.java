package com.greentree.commons.geometry.geom2d.collision;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;

import java.util.Objects;

public class CollisionEvent2D<A extends Collidable2D, B extends Collidable2D> {

    public final A a;
    public final B b;
    public final AbstractVector2f point;

    public final AbstractVector2f normal;

    public final float penetration;

    public CollisionEvent2D(A first, B seconde, AbstractVector2f point, AbstractVector2f normal,
                            float penetration) {
        this.a = first;
        this.b = seconde;
        this.normal = normal;
        this.point = point;
        this.penetration = penetration;
    }

    public CollisionEvent2D(A first, B seconde, Builder builder) {
        this.a = first;
        this.b = seconde;
        this.normal = builder.normal;
        this.point = builder.point;
        this.penetration = builder.penetration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b) + Objects.hash(b, a);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CollisionEvent2D<?, ?> other = (CollisionEvent2D<?, ?>) obj;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b)
                || Objects.equals(b, other.a) && Objects.equals(a, other.b);
    }

    @Override
    public String toString() {
        return "CollisionEvent2D [a=" + a + ", b=" + b + ", point=" + point + ", normal=" + normal
                + ", penetration=" + penetration + "]";
    }

    public static final class Builder {

        public final FinalVector2f point;

        public final FinalVector2f normal;
        public final float penetration;

        public Builder(AbstractVector2f point, AbstractVector2f normal, float penetration) {
            this.point = new FinalVector2f(point);
            this.normal = new FinalVector2f(normal);
            this.penetration = penetration;
        }

        @Override
        public int hashCode() {
            return Objects.hash(normal, penetration, point);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Builder other = (Builder) obj;
            return Objects.equals(normal, other.normal)
                    && Float.floatToIntBits(penetration) == Float.floatToIntBits(other.penetration)
                    && Objects.equals(point, other.point);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "point=" + point +
                    ", normal=" + normal +
                    ", penetration=" + penetration +
                    '}';
        }

        public Builder inverse() {
            final var n = normal.times(-1);
            return new Builder(point, n, penetration);
        }

    }

}
