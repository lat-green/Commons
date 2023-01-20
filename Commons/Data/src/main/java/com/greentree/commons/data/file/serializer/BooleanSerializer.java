package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BooleanSerializer extends PrimitiveSerializer {
	
	public BooleanSerializer() {
		super(Boolean.class);
	}

	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeBoolean((Boolean)obj);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readBoolean();
	}

}
