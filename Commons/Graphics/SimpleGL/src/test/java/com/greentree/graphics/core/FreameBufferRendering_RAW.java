package com.greentree.graphics.core;



import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.GLFWContext;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.Window;
import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;

/** @author Arseny Latyshev */
public abstract class FreameBufferRendering_RAW {
	
	public static void main(final String[] args) throws IOException {
		SGLFW.init();
		try(final var w = new Window("FreameBufferRendering_RAW", 500, 500);) {
			w.makeCurrent();
			w.addSizeCallback((x, y)->glViewport(0, 0, x, y));
			
			glEnable(GL_TEXTURE_2D);
			
			glClearColor(.6f, .6f, .6f, 1);
			glClearDepth(1.0);
			
			final var fb = glGenFramebuffers();
			final var fb_tex = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, fb_tex);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.getWidth(), w.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
			glGenerateMipmap(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, 0);
			
			glBindFramebuffer(GL_FRAMEBUFFER, fb);
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, fb_tex, 0);
			
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			
			while(!w.isShouldClose()) {
				GLFWContext.updateEvents();
				
				glBindFramebuffer(GL_FRAMEBUFFER, fb);
				renderTRI();
				glBindFramebuffer(GL_FRAMEBUFFER, 0);
				render(fb_tex);
				
				w.swapBuffer();
			}
			
			Window.unmakeCurrent();
		}
		SGLFW.terminate();
	}
	
	private static void render() {
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		try(var stack = MemoryStack.stackPush()) {
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
	
	private static void render(int tex) {
		glBindTexture(GL_TEXTURE_2D, tex);
		render();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private static void renderTRI() {
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		try(var stack = MemoryStack.stackPush()) {
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(1, 0, -1, 0, 0, 1));
		}
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glDrawArrays(GLPrimitive.TRIANGLES.glEnum, 0, 3);
		glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
	}
	
}
