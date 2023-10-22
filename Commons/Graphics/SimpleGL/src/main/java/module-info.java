
open module commons.graphics.sgl {
	
	requires transitive commons.math;
	requires transitive commons.image;
	requires transitive commons.action;
	
	requires transitive org.lwjgl;
	requires transitive org.lwjgl.opengl;
	requires transitive org.lwjgl.glfw;
	
	requires java.desktop;
	
	exports com.greentree.common.graphics.sgl;
	exports com.greentree.common.graphics.sgl.callback;
	exports com.greentree.common.graphics.sgl.enums;
	exports com.greentree.common.graphics.sgl.enums.gl;
	exports com.greentree.common.graphics.sgl.enums.gl.target;
	exports com.greentree.common.graphics.sgl.enums.gl.debug;
	exports com.greentree.common.graphics.sgl.enums.gl.param.value;
	exports com.greentree.common.graphics.sgl.enums.gl.param.name;
	exports com.greentree.common.graphics.sgl.font;
	exports com.greentree.common.graphics.sgl.input;
	exports com.greentree.common.graphics.sgl.freambuffer;
	exports com.greentree.common.graphics.sgl.freambuffer.attachment;
	exports com.greentree.common.graphics.sgl.buffer;
	exports com.greentree.common.graphics.sgl.vao;
	exports com.greentree.common.graphics.sgl.shader;
	exports com.greentree.common.graphics.sgl.texture;
	exports com.greentree.common.graphics.sgl.texture.builder;
	exports com.greentree.common.graphics.sgl.texture.gl;
	exports com.greentree.common.graphics.sgl.texture.gl.cubemap;
	
}
