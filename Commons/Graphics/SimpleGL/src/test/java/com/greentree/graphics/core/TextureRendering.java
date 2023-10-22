package com.greentree.graphics.core;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import com.greentree.commons.image.loader.ImageIOImageLoader;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.GLFWContext;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.Window;
import com.greentree.common.graphics.sgl.enums.gl.GLBlendFunc;
import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.texture.builder.TextureBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;

/** @author Arseny Latyshev */
public abstract class TextureRendering {
	
	public static void main(final String[] args) throws IOException {
		SGLFW.init();
		final var ress = TextureRendering.class.getClassLoader();
		try(final var w = new Window("window 1", 500, 500);) {
			w.makeCurrent();
			w.addSizeCallback((x, y)->glViewport(0, 0, x, y));
			
			final GLTexture2DImpl tex1;
			try(final var in = ress.getResourceAsStream("box.jpg")) {
				final var img = ImageIOImageLoader.IMAGE_DATA_LOADER.loadImage(in);
				tex1 = TextureBuilder.builder(img.getData(), GLPixelFormat.gl(img.getFormat())).build2d(img.getWidth(), img.getHeight(), GLPixelFormat.RGBA);
			}
			glEnable(GLEnableTarget.TEXTURE_2D.glEnum);
			
			glClearColor(.6f, .6f, .6f, 1);
			glClearDepth(1.0);
			glEnable(GLEnableTarget.BLEND.glEnum);
			glBlendFunc(GLBlendFunc.SRC_ALPHA.glEnum, GLBlendFunc.ONE_MINUS_SRC_ALPHA.glEnum);
			
			tex1.bind();
			tex1.setFilter(GLFiltering.NEAREST);
			System.out.println(tex1.getWidth() + " " + tex1.getHeight());
			
			glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
			glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
			try(var stack = MemoryStack.stackPush()) {
				glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(-.5f, .5f, -.5f, -.5f, .5f, -.5f, .5f, .5f));
				glTexCoordPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(0,0, 0, 1, 1,1, 1,0));
			}

			glScalef(2, 2, 1);
			
			while(!w.isShouldClose()) {
				GLFWContext.updateEvents();
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
				glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 4);
				w.swapBuffer();
			}
			
			Window.unmakeCurrent();
		}
		SGLFW.terminate();
	}
	
}
