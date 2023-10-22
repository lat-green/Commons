package com.greentree.common.graphics.sgl.enums.gl;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import com.greentree.commons.image.PixelFormat;

/** @author Arseny Latyshev */
public enum GLPixelFormat{
	
	DEPTH_COMPONENT(GL_DEPTH_COMPONENT),STENCIL_INDEX(GL_STENCIL_INDEX),RGB(GL_RGB,PixelFormat.RGB),
	RGBA(GL_RGBA,PixelFormat.RGBA),RED(GL_RED,PixelFormat.RED),RGBA32F(GL_RGBA32F,RGBA),
	RGB32F(GL_RGB32F,RGB),RGBA16F(GL_RGBA16F,RGBA),RGB16F(GL_RGB16F,RGB),;
	
	public final int glEnum;
	private final PixelFormat pf;
	private final GLPixelFormat primitive;
	
	GLPixelFormat(int glEnum) {
		this(glEnum, (PixelFormat) null);
	}
	
	GLPixelFormat(int glEnum, GLPixelFormat pf) {
		this(glEnum, pf.pf, pf);
	}
	
	GLPixelFormat(int glEnum, PixelFormat pf) {
		this(glEnum, pf, null);
	}
	
	GLPixelFormat(int glEnum, PixelFormat pf, GLPixelFormat primitive) {
		this.glEnum = glEnum;
		this.pf = pf;
		this.primitive = primitive != null ? primitive : this;
	}
	
	public static GLPixelFormat gl(int glEnum) {
		for(var pf : values())
			if(pf.glEnum == glEnum)
				return pf;
		return null;
	}
	
	public static GLPixelFormat gl(PixelFormat format) {
		return switch(format) {
			case RED -> RED;
			case RGB -> RGB;
			case RGBA -> RGBA;
			default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}
	
	public GLPixelFormat base() {
		return primitive;
	}
	
	public PixelFormat toPixelFormat() {
		return pf;
	}
	
}
