package com.greentree.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CharSerializer extends PrimitiveSerializer {

	public CharSerializer() {
		super(Character.class);
	}

	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeChar((Character)obj);
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		return in.readChar();
	}


}
