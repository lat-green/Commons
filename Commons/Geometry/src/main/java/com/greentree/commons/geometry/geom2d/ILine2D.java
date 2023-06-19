package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.math.MathLine2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;

public interface ILine2D extends IShape2D {
	
	default MathLine2D getMathLine() {
		return new MathLine2D(p1(), p2());
	}
	
	default AbstractVector2f getNormal() {
		return getMathLine().getNormal();
	}
	
	@Override
	default AbstractVector2f minPoint(AbstractVector2f point) {
		final var mp = getMathLine().minPoint(point);
		
		if(p1().x() < p2().x()) {
			if(p1().x() > mp.x())
				return p1();
			if(p2().x() < mp.x())
				return p2();
		}
		if(p1().x() > p2().x()) {
			if(p1().x() < mp.x())
				return p1();
			if(p2().x() > mp.x())
				return p2();
		}
		
		if(p1().y() < p2().y()) {
			if(p1().y() > mp.y())
				return p1();
			if(p2().y() < mp.y())
				return p2();
		}
		if(p1().y() > p2().y()) {
			if(p1().y() < mp.y())
				return p1();
			if(p2().y() > mp.y())
				return p2();
		}
		
		return mp;
	}
	
	@Override
	default float getPerimeter() {
		return length();
	}
	
	@Override
	default FinalVector2f[] getPoints() {
		return new FinalVector2f[]{p1(),p2()};
	}
	
	@Override
	default int getPointsCount() {
		return 2;
	}
	
	default float length() {
		return Mathf.sqrt(lengthSqr());
	}
	
	default float lengthSqr() {
		return p1().distanceSqr(p2());
	}
	
	@Override
	default ILine2D minLine(AbstractVector2f point) {
		return this;
	}
	
	FinalVector2f p1();
	FinalVector2f p2();
	
}
