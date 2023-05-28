package com.greentree.commons.geometry.geom3d.face;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.math.MathPlane3D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;

public interface Face extends IShape3D {
	
	@Override
	default float getArea() {
		AbstractVector3f v1 = new Vector3f(), v2 = new Vector3f();
		getP1().sub(getP2(), v1);
		getP3().sub(getP2(), v2);
		return Mathf.abs(v1.cross(v2).length()) / 2;
	}
	
	@Override
	default Face[] getFaces() {
		Face[] faces = new Face[1];
		faces[0] = this;
		return faces;
	}
	
	@Override
	default int getFacesCount() {
		return 1;
	}
	
	default MathPlane3D getMathPlane() {
		return new MathPlane3D(getP1(), getP2(), getP3());
	}
	
	default AbstractVector3f getNormal() {
		return getMathPlane().getNormal();
	}
	
	AbstractVector3f getP1();
	
	AbstractVector3f getP2();
	
	AbstractVector3f getP3();
	
	default boolean isClockwise(final AbstractVector3f normal) {
		var p1 = getP1().getProjection(normal);
		var p2 = getP2().getProjection(normal);
		var p3 = getP3().getProjection(normal);
		return Shape2DUtil.isClockwise(p1, p2, p3);
	}
	
	default boolean isConvex() {
		return getMathPlane().getD() > 0;
	}
	
	@Override
	default AbstractVector3f minPoint(final AbstractVector3f point) {
		AbstractVector3f res = null, pi;
		float dis, dis0 = Float.MAX_VALUE;
		for(final var f : getFaces()) {
			pi = f.minPoint(point);
			dis = pi.distanceSquared(point);
			if(dis < dis0) {
				dis0 = dis;
				res = pi;
			}
		}
		return res;
	}
	
}
