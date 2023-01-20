package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DoubleSerializer extends PrimitiveSerializer {

	public DoubleSerializer() {
		super(Double.class);
	}

	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeDouble((Double)obj);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readDouble();
	}


}
