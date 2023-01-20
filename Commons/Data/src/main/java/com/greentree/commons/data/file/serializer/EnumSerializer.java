package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class EnumSerializer extends PrimitiveSerializer {

	public EnumSerializer() {
		super(Enum.class);
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	protected Object read(Class<?> type, DataInput in) throws IOException {
		final var name = in.readUTF();
		return Enum.valueOf((Class) type, name);
	}

	@Override
	protected void write(Object obj, DataOutput out) throws IOException {
		final var e = (Enum<?>) obj;
		final var name = e.name();
		out.writeUTF(name);
	}

}
