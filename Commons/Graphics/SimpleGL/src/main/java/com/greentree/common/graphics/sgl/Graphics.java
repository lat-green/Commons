package com.greentree.common.graphics.sgl;

import static com.greentree.common.graphics.sgl.enums.gl.GLClearBit.*;
import static com.greentree.common.graphics.sgl.enums.gl.GLPrimitive.*;
import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLParam.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.enums.gl.GLClearBit;
import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.commons.image.Color;

/** @author Arseny Latyshev */
public abstract class Graphics {

//	static {
//		glEnable(BLEND);
//		glBlendFunc(SRC_ALPHA, ONE_MINUS_SRC_ALPHA);
//		glDisable(BLEND);
//	}
//
//	static {
//		glEnable(DEBUG_OUTPUT);
//		glEnable(DEBUG_OUTPUT_SYNCHRONOUS);
//		glDebugMessageCallback((source, type, id, severity, message, userParam) -> {
//			if(severity != DEBUG_SEVERITY_NOTIFICATION) {
//				final var severity0 = severity.name;
//				String source0 = source.name;
//				String type0 = type.name;
//				System.err.printf("openGl error (Source: %s Type: %s ID: %d Severity: %s) message:%s\n", source0, type0, id, severity0, message);
//			}
//		}, 0);
//	}

//	public static final float[] array2f_05 = {-.5f, .5f, -.5f, -.5f, .5f, -.5f, .5f, .5f};

	public static void bindColor(Color c) {
		glColor4f(c.r, c.g, c.b, c.a);
	}

//	public static void clearColor(final float r, final float g, final float b) {
//		glClearColor(r, g, b, 1);
//	}

//	public static void disableBlead() {
//		glDisable(BLEND);
//	}
//
//	public static void disableCullFace() {
//		glDisable(CULL_FACE);
//	}
//
//	public static void disableDepthTest() {
//		glDisable(DEPTH_TEST);
//	}

	public static void drawArc(final float x1, final float y1, final float width, final float height, final float start,
			final float end) {
		Graphics.drawArc(x1, y1, width, height, 50, start, end);
	}

	public static void drawArc(final float x1, final float y1, final float width, final float height,//TODO
			final int segments, final float start, float end0) {
		while(end0 < start) end0 += 360.0f;
		final float cx = x1;
		final float cy = y1;
		final var end = end0;
		glBegin(LINE_STRIP.glEnum);
		for(float step = 360f / segments, a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + Math.cos(Math.toRadians(ang) - Math.PI / 2) * width);
			final float y2 = (float) (cy + Math.sin(Math.toRadians(ang) - Math.PI / 2) * height);
			glVertex2f(x2, y2);
		}
		glEnd();
	}

	public static void drawCircle(float x, float y, float radius) {
		drawOval(x, y, radius, radius);
	}

	public static void drawGradientLine(final float x1, final float y1, final Color color1, final float x2,
			final float y2, final Color color2) {
		glBegin(LINES.glEnum);
		bindColor(color1);
		glVertex2f(x1, y1);
		bindColor(color2);
		glVertex2f(x2, y2);
		glEnd();
	}

	public static void drawGradientLine(final float x1, final float y1, final float red1, final float green1,
			final float blue1, final float alpha1, final float x2, final float y2, final float red2, final float green2,
			final float blue2, final float alpha2) {
		glBegin(LINES.glEnum);
		glColor4f(red1, green1, blue1, alpha1);
		glVertex2f(x1, y1);
		glColor4f(red2, green2, blue2, alpha2);
		glVertex2f(x2, y2);
		glEnd();
	}

	public static void drawLine(final float x1, final float y1, final float x2, final float y2) {
		glBegin(LINES.glEnum);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
	}

	public static void drawOval(final float x1, final float y1, final float width, final float height) {
		Graphics.drawOval(x1, y1, width, height, 50);
	}

	public static void drawOval(final float x1, final float y1, final float width, final float height,
			final int segments) {
		Graphics.drawArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}

	public static void drawRect(final float x1, final float y1, final float width, final float height) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(x1, y1, x1 + width, y1, x1 + width, y1 + height, x1, y1 + height));
			glDrawArrays(GLPrimitive.LINE_LOOP.glEnum, 0, 4);
			glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		}
	}

	public static void drawRoundRect(final float x, final float y, final float width, final float height,
			final int cornerRadius) {
		Graphics.drawRoundRect(x, y, width, height, cornerRadius, 50);
	}

	public static void drawRoundRect(final float x, final float y, final float width, final float height,
			int cornerRadius, final int segs) {
		if(cornerRadius < 0) throw new IllegalArgumentException("corner radius must be > 0");
		if(cornerRadius == 0) {
			Graphics.drawRect(x, y, width, height);
			return;
		}
		final int mr = (int) Math.min(width, height) / 2;
		if(cornerRadius > mr) cornerRadius = mr;
		Graphics.drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
		Graphics.drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
		Graphics.drawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius);
		Graphics.drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height);
		final float d = cornerRadius * 2;
		Graphics.drawArc(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
		Graphics.drawArc(x, y + height - d, d, d, segs, 90.0f, 180.0f);
		Graphics.drawArc(x + width - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.drawArc(x, y, d, d, segs, 180.0f, 270.0f);
	}

	public static void drawVector(final float x, final float y, final float vx, final float vy) {
		Graphics.drawLine(x, y, x + vx, y + vy);
	}

//	public static void enableBlead() {
//		glEnable(BLEND);
//	}
//
//	public static void enableCullFace() {
//		glEnable(CULL_FACE);
//	}
//
//	public static void enableDepthTest() {
//		glEnable(DEPTH_TEST);
//	}

	public static void fillArc(final float x1, final float y1, final float width, final float height, final float start,
			final float end) {
		Graphics.fillArc(x1, y1, width, height, 30, start, end);
	}

	public static void fillArc(final float x1, final float y1, final float width, final float height,//TODO
			final int segments, final float start, float end) {
		while(end < start) end += 360.0f;
		final float cx = x1;
		final float cy = y1;
		glBegin(POLYGON.glEnum);
		final int step = 360 / segments;
		glVertex2f(cx, cy);
		for(int a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + Math.cos(Math.toRadians(ang)) * width);
			final float y2 = (float) (cy + Math.sin(Math.toRadians(ang)) * height);
			glVertex2f(x2, y2);
		}
		glEnd();
	}

	public static void fillCircle(float x, float y, float radius) {
		fillOval(x, y, radius, radius);
	}

	public static void fillOval(final float x1, final float y1, final float width, final float height) {
		Graphics.fillOval(x1, y1, width, height, 50);
	}

	public static void fillOval(final float x1, final float y1, final float width, final float height,
			final int segments) {
		Graphics.fillArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}

	public static void fillRect(final double x1, final double y1, final double width, final double height) {
		glBegin(QUADS.glEnum);
		glVertex2d(x1, y1 + height);
		glVertex2d(x1 + width, y1 + height);
		glVertex2d(x1 + width, y1);
		glVertex2d(x1, y1);
		glEnd();
	}

	public static void fillRoundRect(final float x, final float y, final float width, final float height,
			final int cornerRadius) {
		Graphics.fillRoundRect(x, y, width, height, cornerRadius, 50);
	}

	public static void fillRoundRect(final float x, final float y, final float width, final float height,
			int cornerRadius, final int segs) {
		if(cornerRadius < 0) throw new IllegalArgumentException("corner radius must be > 0");
		if(cornerRadius == 0) {
			Graphics.fillRect(x, y, width, height);
			return;
		}
		final int mr = (int) Math.min(width, height) / 2;
		if(cornerRadius > mr) cornerRadius = mr;
		final float d = cornerRadius * 2;
		Graphics.fillRect(x + cornerRadius, y, width - d, cornerRadius);
		Graphics.fillRect(x, y + cornerRadius, cornerRadius, height - d);
		Graphics.fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - d);
		Graphics.fillRect(x + cornerRadius, y + height - cornerRadius, width - d, cornerRadius);
		Graphics.fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
		Graphics.fillArc(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
		Graphics.fillArc(x, y + height - d, d, d, segs, 90.0f, 180.0f);
		Graphics.fillArc(x + width - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.fillArc(x, y, d, d, segs, 180.0f, 270.0f);
	}

	public static void frustum(double l, double r, double b, double t, double n, double f) {
		glFrustum(l, r, b, t, n, f);
	}

	public static FloatBuffer getModelViewMatrix() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			glGetFloatv(MODELVIEW_MATRIX.glEnum, buf);
			return buf;
		}
	}

	public static FloatBuffer getProjectionMatrix() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			glGetFloatv(PROJECTION_MATRIX.glEnum, buf);
			return buf;
		}
	}

	public static float[] getTexArray2f(float tex_width, float tex_height, float width, float height) {
		final float w = .5f / width, h = .5f / height;
		return new float[]{w, h, w, tex_height - h, tex_width - w, tex_height - h, tex_width - w, h};
	}

	public static void glClearAll() {
		glClear(GLClearBit.gl(COLOR_BUFFER_BIT, DEPTH_BUFFER_BIT, ACCUM_BUFFER_BIT, STENCIL_BUFFER_BIT));
	}

	public static void pointSize(float size) {
		glPointSize(size);
	}

	public static void popMatrix() {
		glPopMatrix();
	}
	public static void pushMatrix() {
		glPushMatrix();
	}

	public static void rotate(final double rx, final double ry, final double rz) {
		glRotated(rx, 1.0f, 0.0f, 0.0f);
		glRotated(ry, 0.0f, 1.0f, 0.0f);
		glRotated(rz, 0.0f, 0.0f, 1.0f);
	}

	public static void scale(final float sx, final float sy) {
		glScalef(sx, sy, 1.0f);
	}

	public static void scale(final float x, final float y, final float z) {
		glScalef(x, y, z);
	}

	public static void translate(final float x, final float y) {
		glTranslatef(x, y, 0.0f);
	}

	public static void translate(final float x, final float y, final float z) {
		glTranslatef(x, y, z);
	}

	public static void widthLine(int i) {
		glLineWidth(i);
	}

}
