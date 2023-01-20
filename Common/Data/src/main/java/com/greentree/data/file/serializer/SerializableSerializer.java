package com.greentree.data.file.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.greentree.common.util.classes.info.TypeInfo;
import com.greentree.common.util.exception.WrappedException;
import com.greentree.data.file.DataFileReader;
import com.greentree.data.file.DataFileWriter;

public class SerializableSerializer extends AbstractSerializer {

	public SerializableSerializer() {
		super(Serializable.class);
	}

	@Override
	public int priority(TypeInfo<?> type) {
		return 20;
	}

	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		try(final var s = new ByteArrayOutputStream()) {
			try(final var oout = new ObjectOutputStream(s)) {
				oout.writeObject(obj);
			}
			out.writeInt(s.toByteArray().length);
			out.write(s.toByteArray());
		}
	}

	@Override
	protected Object read(Class<?> cls, DataInput in, DataFileReader file) throws IOException {
		final var len = in.readInt();
		final var bs = new byte[len];
		in.readFully(bs);
		try(final var s = new ByteArrayInputStream(bs)) {
			try(final var oin = new ObjectInputStream(s)) {
				return oin.readObject();
			}catch(ClassNotFoundException e) {
				throw new WrappedException(e);
			}
		}
	}

}
