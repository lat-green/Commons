package com.greentree.graphics.core;


import java.io.IOException;
import java.io.InputStream;

import com.greentree.common.graphics.sgl.GLFWContext;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.texture.builder.TextureBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.commons.image.loader.PNGImageData;

/** @author Arseny Latyshev */
public abstract class Main {
	
	public static void main(final String[] args) throws IOException, InterruptedException {
		try {
			solve();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void solve() throws Exception {
		SGLFW.init();
		final var ress = Main.class.getClassLoader();
		try(final var w = new GLFWContext("Main", 500, 500, true, false, false, true, null);) {
			w.makeCurrent();
			create_texture_null(ress.getResourceAsStream("mag(2).png"));
			//			w.makeCurrent();
			//			w.addSizeCallback((x, y)->glViewport(0, 0, x, y));
			//			
			//			
			//			final var tex1 = create_texture(ress.getResourceAsStream("mag(2).png"));
			//			glEnable(GLEnableTarget.TEXTURE_2D.glEnum);
			//			
			//			glClearColor(.6f, .6f, .6f, 1);
			//			glClearDepth(1.0);
			//			glEnable(GLEnableTarget.BLEND.glEnum);
			//			glBlendFunc(GLBlendFunc.SRC_ALPHA.glEnum, GLBlendFunc.ONE_MINUS_SRC_ALPHA.glEnum);
			//			
			//			tex1.bind();
			//			
			//			glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
			//			glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
			//			try(var stack = MemoryStack.stackPush()) {
			//				glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(-.5f, .5f, -.5f, -.5f, .5f, -.5f, .5f, .5f));
			//				glTexCoordPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(0, 0, 0, 1, 1, 1, 1, 0));
			//			}
			//			
			//			while(!w.isShouldClose()) {
			//				GLFWContext.updateEvents();
			//				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			//				glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 4);
			//				w.swapBuffer();
			//			}
			//			
			//			Window.unmakeCurrent();
			int t = 100;
			while(t-- > 0) {
				System.gc();
				w.pollDelete();
				Thread.sleep(100);
			}
			GLFWContext.unmakeCurrent();
		}
		SGLFW.terminate();
	}
	
	private static GLTexture2DImpl create_texture(InputStream in) throws IOException {
		final GLTexture2DImpl tex0;
		try(in) {
			final var img = PNGImageData.IMAGE_DATA_LOADER.loadImage(in);
			tex0 = TextureBuilder.builder(img.getData(), GLPixelFormat.gl(img.getFormat()))
					.build2d(img.getWidth(), img.getHeight(), GLPixelFormat.RGBA);
		}
		tex0.setFilter(GLFiltering.NEAREST);
		return tex0;
	}
	
	private static void create_texture_null(InputStream in) throws IOException {
		create_texture(in);
	}
	
}
