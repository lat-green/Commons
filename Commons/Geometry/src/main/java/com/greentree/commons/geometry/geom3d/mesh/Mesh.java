package com.greentree.commons.geometry.geom3d.mesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.geom3d.face.Face;
import com.greentree.commons.math.vector.AbstractVector3f;

public class Mesh implements IShape3D {

	private final IndexFace[] faces;
	private final AbstractVector3f[] vetrex;

	public Mesh(final List<AbstractVector3f> vetrex, final Collection<Builder.int3> facesC) {
		this.vetrex = new AbstractVector3f[vetrex.size()];
		vetrex.toArray(this.vetrex);
		var faces = new Builder.int3[facesC.size()];
		facesC.toArray(faces);
		this.faces = new IndexFace[facesC.size()];
		for(int i = 0; i < faces.length; i++) {
			var f = faces[i];
			this.faces[i] = new IndexFace(f.a, f.b, f.c);
		}
	}

	public float[] get() {
		final float[] res = new float[3 * 3 * faces.length];
		for(int i = 0; i < faces.length; i++) {
			var f = faces[i];
			int i9 = 9*i;
			res[i9+0] = f.getP1().x();
			res[i9+1] = f.getP1().y();
			res[i9+2] = f.getP1().z();
			res[i9+3] = f.getP2().x();
			res[i9+4] = f.getP2().y();
			res[i9+5] = f.getP2().z();
			res[i9+6] = f.getP3().x();
			res[i9+7] = f.getP3().y();
			res[i9+8] = f.getP3().z();
			i++;
		}
		return res;
	}

	@Override
	public Face[] getFaces() {
		return faces;
	}

	public static class Builder {

		private final List<AbstractVector3f> vertices;

		private final List<int3> faces;
		public Builder() {
			faces      = new ArrayList<>();
			vertices = new ArrayList<>();
		}

		public void addFaces(final int3 face) {
			faces.add(face);
		}

		public void addVertices(final AbstractVector3f vector3f) {
			vertices.add(vector3f);
		}

		public Mesh build() {
			return new Mesh(vertices, faces);
		}

		public static class int3 {
			private final int a, b, c;

			public int3(int a, int b, int c) {
				this.a = a;
				this.b = b;
				this.c = c;
			}

		}
	}

	public class IndexFace implements Face {

		private final int[] vertexIndices = new int[3];

		public IndexFace(int a, int b, int c) {
			vertexIndices[0] = a;
			vertexIndices[1] = b;
			vertexIndices[2] = c;
		}

		@Override
		public AbstractVector3f getP1() {
			return vetrex[vertexIndices[0]];
		}

		@Override
		public AbstractVector3f getP2() {
			return vetrex[vertexIndices[1]];
		}

		@Override
		public AbstractVector3f getP3() {
			return vetrex[vertexIndices[2]];
		}

	}

}
