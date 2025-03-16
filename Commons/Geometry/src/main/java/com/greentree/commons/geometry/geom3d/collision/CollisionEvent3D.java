package com.greentree.commons.geometry.geom3d.collision;

import com.greentree.commons.geometry.geom3d.IShape3D;
import org.joml.Vector3fc;

import java.util.Objects;

public class CollisionEvent3D<A extends IShape3D, B extends IShape3D> {

    public final A a;
    public final B b;
    public final Vector3fc point;
    public final Vector3fc normal;
    public final float penetration;

    public CollisionEvent3D(A first, B seconde, Vector3fc point, Vector3fc normal,
                            float penetration) {
        this.a = first;
        this.b = seconde;
        this.normal = normal;
        this.point = point;
        this.penetration = penetration;
    }

    public CollisionEvent3D(A first, B seconde, Builder builder) {
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
        CollisionEvent3D<?, ?> other = (CollisionEvent3D<?, ?>) obj;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b)
               || Objects.equals(b, other.a) && Objects.equals(a, other.b);
    }

    @Override
    public String toString() {
        return "CollisionEvent2D [a=" + a + ", b=" + b + ", point=" + point + ", normal=" + normal
               + ", penetration=" + penetration + "]";
    }

    public static class Builder {

        public Vector3fc point;
        public Vector3fc normal;
        public float penetration;

        public Builder(Vector3fc point, Vector3fc normal, float penetration) {
            setPoint(point);
            setNormal(normal);
            setPenetration(penetration);
        }

        /** @return the point */
        public Vector3fc getPoint() {
            return point;
        }

        /** @param point the point to set */
        public void setPoint(Vector3fc point) {
            this.point = point;
        }

        /** @return the normal */
        public Vector3fc getNormal() {
            return normal;
        }

        /** @param normal the normal to set */
        public void setNormal(Vector3fc normal) {
            this.normal = normal;
        }

        /** @return the penetration */
        public float getPenetration() {
            return penetration;
        }

        /** @param penetration the penetration to set */
        public void setPenetration(float penetration) {
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

    }

}
