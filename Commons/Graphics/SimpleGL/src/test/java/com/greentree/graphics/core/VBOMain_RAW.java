package com.greentree.graphics.core;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;

public abstract class VBOMain_RAW {

	public static void main(final String[] args) throws IOException {
		glfwInit();

		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);

		final var w = glfwCreateWindow(800, 600, "", 0, 0);

		glfwMakeContextCurrent(w);
		GL.createCapabilities(false);

		glBegin(GL_TRIANGLES);
		glVertex2f(-.5f, -.5f);
		glVertex2f(.5f, -.5f);
		glVertex2f(0, .5f);
		glEnd();

		glfwSwapBuffers(w);

		while(!glfwWindowShouldClose(w))
			glfwPollEvents();

		glfwTerminate();
	}

}
