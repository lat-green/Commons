package com.greentree.commons.geometry.geom3d.collision;

import java.util.Objects;

import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.math.vector.AbstractVector3f;

public class CollisionEvent3D<A extends IShape3D, B extends IShape3D> {
	
	public final A a;
	public final B b;
	public final AbstractVector3f point;
	public final AbstractVector3f normal;
	public final float penetration;
	
	public CollisionEvent3D(A first, B seconde, AbstractVector3f point, AbstractVector3f normal,
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
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		CollisionEvent3D<?, ?> other = (CollisionEvent3D<?, ?>) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b)
				|| Objects.equals(b, other.a) && Objects.equals(a, other.b);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b) + Objects.hash(b, a);
	}
	
	@Override
	public String toString() {
		return "CollisionEvent2D [a=" + a + ", b=" + b + ", point=" + point + ", normal=" + normal
				+ ", penetration=" + penetration + "]";
	}
	
	public static class Builder {
		
		public AbstractVector3f point;
		public AbstractVector3f normal;
		public float penetration;
		
		
		/** @return the point */
		public AbstractVector3f getPoint() {
			return point;
		}

		
		/** @param point the point to set */
		public void setPoint(AbstractVector3f point) {
			this.point = point;
		}
		
		
		/** @return the normal */
		public AbstractVector3f getNormal() {
			return normal;
		}
		
		
		/** @param normal the normal to set */
		public void setNormal(AbstractVector3f normal) {
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
		
		public Builder(AbstractVector3f point, AbstractVector3f normal, float penetration) {
			setPoint(point);
			setNormal(normal);
			setPenetration(penetration);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null || getClass() != obj.getClass())
				return false;
			Builder other = (Builder) obj;
			return Objects.equals(normal, other.normal)
					&& Float.floatToIntBits(penetration) == Float.floatToIntBits(other.penetration)
					&& Objects.equals(point, other.point);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(normal, penetration, point);
		}
		
	}
	
}
