package com.greentree.graphics.core;



import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.GLFWContext;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.Window;
import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;

/** @author Arseny Latyshev */
public abstract class FreameBufferRendering {
	
	public static void main(final String[] args) throws IOException {
		SGLFW.init();
		try(final var w = new Window("FreameBufferRendering", 500, 500);) {
			w.makeCurrent();
			w.addSizeCallback((x, y)->glViewport(0, 0, x, y));
			
			glEnable(GLEnableTarget.TEXTURE_2D.glEnum);
			
			glClearColor(.6f, .6f, .6f, 1);
			glClearDepth(1.0);
			
			final var fb = new FreamBufferBuilder().addColor2D(GLPixelFormat.RGB).build(w.getWidth(), w.getHeight());
			
			while(!w.isShouldClose()) {
				GLFWContext.updateEvents();
				
				fb.bind();
				renderTRI();
				fb.unbind();
				render(fb.getColorTexture(0));
				
				w.swapBuffer();
			}
			
			Window.unmakeCurrent();
		}
		SGLFW.terminate();
	}
	
	private static void render(GLTexture tex) {
		tex.bind();
		render();
		tex.unbind();
	}
	
	private static void render() {
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		try(MemoryStack stack = MemoryStack.stackPush()) {
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(-.5f, .5f, -.5f, -.5f, .5f, -.5f, .5f, .5f));
			glTexCoordPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(0, 0, 0, 1, 1, 1, 1, 0));
		}
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glPushMatrix();
		glScalef(2, 2, 1);
		glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 4);
		glPopMatrix();
		glDisableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
	}
	
	private static void renderTRI() {
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		try(MemoryStack stack = MemoryStack.stackPush()) {
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(1, 0, -1, 0, 0, 1));
		}
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glDrawArrays(GLPrimitive.TRIANGLES.glEnum, 0, 3);
		glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
	}
	
}
