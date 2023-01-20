package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;
import com.greentree.commons.data.file.DataFiles;
import com.greentree.commons.data.file.DataSerialization;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.cortege.Pair;


public abstract class PrimitiveSerializer extends AbstractSerializer {
	
	public PrimitiveSerializer(Class<?> root) {
		super(root);
	}

	public PrimitiveSerializer(TypeInfo<?> root) {
		super(root);
	}

	@Override
	protected Object read(Class<?> type, DataInput in, DataFileReader file) throws IOException {
		return read(type, in);
	}
	
	protected abstract Object read(Class<?> type, DataInput in) throws IOException;

	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		write(obj, out);
	}
	
	protected abstract void write(Object obj, DataOutput out) throws IOException;

	@Override
	public <T> void write(T last, T obj, DataOutput out, DataFileWriter file) throws IOException {
		final var a = DataFiles.write(last, file).data();
		final var b = DataFiles.write(obj, file).data();

		if(a.length != b.length) {
			out.writeInt(-1);
			write(obj, out, file);
			return;
		}
		
		var fs = new byte[a.length];
		
		for(var i = 0; i < fs.length; i++)
			fs[i] = (byte) (b[i] - a[i]);

		fs = DataSerialization.zeroReduce(fs);
		out.writeInt(fs.length);
		out.write(fs);
	}
	
	@Override
	public Pair<Object, Runnable> read(Object last, TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException {
		final var s = in.readInt();
		if(s == -1) {
			return read(type, in, file);
		}
		final var a = DataFiles.write(last, null).data();
		final var b = new byte[a.length];
		var fs = new byte[s];
		in.readFully(fs);
		fs = DataSerialization.zeroDereduce(fs);

		for(var i = 0; i < fs.length; i++)
			b[i] = (byte) (a[i] + fs[i]);
		
		return DataFiles.get(type, b, file);
	}
	
}
