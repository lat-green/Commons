package com.greentree.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatSerializer extends PrimitiveSerializer {

	public FloatSerializer() {
		super(Float.class);
	}

	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeFloat((Float)obj);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readFloat();
	}


}
