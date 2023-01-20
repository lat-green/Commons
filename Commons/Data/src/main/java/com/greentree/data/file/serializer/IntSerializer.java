package com.greentree.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntSerializer extends PrimitiveSerializer {

	public IntSerializer() {
		super(Integer.class);
	}

	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeInt((Integer)obj);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readInt();
	}

}
