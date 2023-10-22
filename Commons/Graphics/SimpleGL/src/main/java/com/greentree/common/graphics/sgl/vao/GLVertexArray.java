package com.greentree.common.graphics.sgl.vao;

import static org.lwjgl.opengl.GL45.*;

import java.nio.Buffer;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.buffer.ArrayBuffer;
import com.greentree.common.graphics.sgl.buffer.ElementArrayBuffer;
import com.greentree.common.graphics.sgl.buffer.VideoBuffer;
import com.greentree.commons.util.iterator.IteratorUtil;

import javax.xml.stream.Location;

/** @author Arseny Latyshev */
public class GLVertexArray extends GLObject {
	
	public static final GLVertexArrayBinder BINDER = new GLVertexArrayBinder();
	
	private final int size;
	private final Iterable<? extends AttributeGroup> attributes;

	public GLVertexArray.AttributeGroup getLocation(int location) {
		for(var attribute : attributes)
			if(attribute.location == location)
				return attribute;
		return null;
	}

	public GLVertexArray(Iterable<? extends AttributeGroup> attributeGroups) {
		super(glGenVertexArrays());
		this.attributes = attributeGroups;
		bind();
		int size = 0;
		for(var location : attributeGroups) {
			location.vbo.bind();
			glEnableVertexAttribArray(location.location);
			glVertexAttribPointer(location.location, location.size, location.vbo.getDataType().glEnum, false, location.stride, location.offset);
			glVertexAttribDivisor(location.location, location.divisor);
			if(location.divisor == 0)
				size = location.vbo.size() * location.vbo.getDataType().size / location.stride;
		}
		ArrayBuffer.BINDER.unbind();
		this.size = size;
		BINDER.unbind();
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
	@Override
	public void delete() {
		glDeleteVertexArrays(glID());
	}
	
	public int size() {
		return size;
	}
	
	public static final class AttributeGroup {

		public final int location;
		public final ArrayBuffer<?> vbo;
		public final int size;
		public final int stride;
		public final int offset;
		public final int divisor;

		public AttributeGroup(int location, ArrayBuffer<?> vbo, int size, int stride, int offset, int divisor) {
			this.location = location;
			this.vbo = vbo;
			this.size = size;
			this.stride = stride;
			this.offset = offset;
			this.divisor = divisor;
		}

		public AttributeGroup(int location, ArrayBuffer<?> vbo, int size) {
			this(location, vbo, size, vbo.getDataType().size * size, 0, 0);
		}

		public AttributeGroup(int location, ArrayBuffer<?> vbo, int size, int offset) {
			this(location, vbo, size, vbo.getDataType().size * size, offset, 0);
		}

	}
	
	public static final class GLVertexArrayBinder extends GLObjectBinder {
		
		private GLVertexArrayBinder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glBindVertexArray(glID);
		}
		
	}
	
}
