package com.greentree.commons.geometry.geom3d;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.geometry.geom3d.face.BasicFace;
import com.greentree.commons.geometry.geom3d.shape.Box;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.Vector3f;

public class Shape3DUtilTest {
	
	private Vector3f vec(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}
	
	@Test
	void BoxIsInside() {
		IShape3D sh = new Box(vec(0, 0, 0), vec(1, 1, 1));
		
		assertTrue(sh.isInside(vec(0, 0, 0)));
		assertTrue(sh.isInside(vec(0.05f, 0, 0)));
		assertTrue(sh.isInside(vec(0.25f, 0, 0)));
		assertFalse(sh.isInside(vec(10, 0, 0)));
		assertFalse(sh.isInside(vec(10, 10, 0)));
	}
	
	
	//	@Test
	//	void getProjection() {
	//		IShape3D sh1 = new Box(of(0,0,0), of(1,1,1));
	//		IShape3D sh2 = new BasicFace(of(1,0,0),of(0,0,0),of(0,1,0));
	//
	//		var n = sh2.getNormals()[0];
	//
	//		System.out.println(Arrays.toString(sh1.getProjection(n)));
	//		System.out.println(Arrays.toString(sh2.getProjection(n)));
	//	}
	
	@Test
	void getSin() {
		final var sin = Shape3DUtil.getSin(vec(1, 0, 0), vec(0, 0, 0), vec(0, 1, 0));
		assertTrue(Mathf.equals(1, sin), "sin:" + sin);
	}
	
	@Test
	void isIntersectOnNormalProjectionFalse() {
		IShape3D sh1 = new Box(vec(0, 0, 0), vec(1, 1, 1));
		IShape3D sh2 = new BasicFace(vec(1, 0, 0), vec(0, 0, 0), vec(0, 1, 0));
		
		final var n = new Vector3f(1, 1, 0).normalize(1);
		
		final var p1 = Shape3DUtil.getProjectionPoligon(sh1, n);
		final var p2 = Shape3DUtil.getProjectionPoligon(sh2, n);
		
		//		System.out.println(p1);
		//		System.out.println(p2);
		//		System.out.println(p1.isIntersect(p2));
		
		//		assertTrue(Shape3DUtil.isIntersectOnNormalProjection(sh1, sh2));
	}
	
	@Test
	void isIntersectOnNormalProjectionTrue() {
		IShape3D sh1 = new Box(vec(0, 0, 0), vec(1, 1, 1));
		IShape3D sh2 = new BasicFace(vec(1, 0, 0), vec(0, 0, 0), vec(0, 1, 0));
		
		//		assertTrue(Shape3DUtil.isIntersectOnNormalProjection(sh1, sh2));
	}
}
