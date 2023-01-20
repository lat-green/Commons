package com.greentree.commons.geometry.geom3d.shape;

import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.geom3d.face.BasicFace;
import com.greentree.commons.geometry.geom3d.face.Face;
import com.greentree.commons.math.vector.Vector3f;


public class Box implements IShape3D {
	
	private final Face[] FACES;
	
	public Box(Vector3f pos, Vector3f scale) {
		var p000 = new Vector3f(0, 0, 0).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p100 = new Vector3f(1, 0, 0).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p010 = new Vector3f(0, 1, 0).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p110 = new Vector3f(1, 1, 0).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p001 = new Vector3f(0, 0, 1).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p101 = new Vector3f(1, 0, 1).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p011 = new Vector3f(0, 1, 1).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		var p111 = new Vector3f(1, 1, 1).add(-.5f, -.5f, -.5f).mul(scale).add(pos);
		
		FACES = new Face[12];
		
		FACES[0] = new BasicFace(p010, p100, p000);
		FACES[1] = new BasicFace(p110, p100, p010);
		
		FACES[2] = new BasicFace(p101, p011, p001);
		FACES[3] = new BasicFace(p101, p111, p011);
		
		FACES[4] = new BasicFace(p000, p001, p010);
		FACES[5] = new BasicFace(p010, p001, p011);
		
		FACES[6] = new BasicFace(p100, p110, p101);
		FACES[7] = new BasicFace(p110, p111, p101);
		
		FACES[8] = new BasicFace(p000, p001, p100);
		FACES[9] = new BasicFace(p100, p001, p101);
		
		FACES[10] = new BasicFace(p010, p110, p011);
		FACES[11] = new BasicFace(p110, p111, p011);
	}
	
	@Override
	public Face[] getFaces() {
		return FACES;
	}
	
}
