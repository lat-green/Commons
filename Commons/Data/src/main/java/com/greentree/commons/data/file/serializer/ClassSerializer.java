package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.greentree.commons.util.exception.WrappedException;

public class ClassSerializer extends PrimitiveSerializer {

	public ClassSerializer() {
		super(Class.class);
	}
	
	@Override
	public void write(Object obj, DataOutput out) throws IOException {
		out.writeUTF(((Class<?>)obj).getName());
	}

	@Override
	public Object read(Class<?> cls, DataInput in) throws IOException {
		try {
			return Class.forName(in.readUTF());
		}catch(ClassNotFoundException | IOException e) {
			throw new WrappedException(e);
		}
	}

}
