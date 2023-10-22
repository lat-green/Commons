package com.greentree.common.graphics.sgl.shader;


import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

/** @author Arseny Latyshev */
public final class GLUniformLocation {
	
	public final int glID;
	
	public GLUniformLocation(int id) {
		glID = id;
	}
	
	public void rgb(Color color) {
		setf(color.r, color.g, color.b);
	}
	
	public void rgba(Color color) {
		glUniform4f(glID, color.r, color.g, color.b, color.a);
	}
	
	public void set4fv(FloatBuffer matrix) {
		glUniformMatrix4fv(glID, false, matrix);
	}
	
	public void set4fv(int offset, FloatBuffer matrix) {
		glUniformMatrix4fv(glID + offset, false, matrix);
	}
	
	public void setf(AbstractVector2f f) {
		glUniform2f(glID, f.x(), f.y());
	}
	
	public void setf(AbstractVector3f f) {
		glUniform3f(glID, f.x(), f.y(), f.z());
	}
	
	public void setf(double x) {
		glUniform1f(glID, (float) x);
	}
	
	public void setf(float x) {
		glUniform1f(glID, x);
	}
	
	public void setf(float x, float y) {
		glUniform2f(glID, x, y);
	}
	
	public void setf(float x, float y, float z) {
		glUniform3f(glID, x, y, z);
	}
	
	public void setf(float x, float y, float z, float w) {
		glUniform4f(glID, x, y, z, w);
	}
	
	public void setfi(int offset, float x) {
		glUniform1f(glID + offset, x);
	}
	
	public void setfi(int offset, float x, float y) {
		glUniform2f(glID + offset, x, y);
	}
	
	public void setfi(int offset, float x, float y, float z) {
		glUniform3f(glID + offset, x, y, z);
	}
	
	public void setfi(int offset, float x, float y, float z, float w) {
		glUniform4f(glID + offset, x, y, z, w);
	}
	
	public void seti(int i) {
		glUniform1i(glID, i);
	}
	
	@Override
	public String toString() {
		return "GLLocation [id=" + glID + "]";
	}
	
}
