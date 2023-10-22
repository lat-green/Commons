package com.greentree.common.graphics.sgl.enums.gl.target;

public interface GLTextureEnumTarget extends GLEnumTarget {

	static GLTextureEnumTarget get(int glEnum) {
		GLTextureEnumTarget res = null;
		if(res == null) res = GLCubeMapTextureEnumTarget.get(glEnum);
		if(res == null) res = GLTexture1DEnumTarget.get(glEnum);
		if(res == null) res = GLTexture2DEnumTarget.get(glEnum);
		if(res == null) res = GLTexture3DEnumTarget.get(glEnum);
		return null;
	}

}
