package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;

public class ArraySerializer extends AbstractSerializer {

	
	public ArraySerializer() {
		super(Object[].class);
	}
	
	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		final var size = Array.getLength(obj);
    	out.writeInt(size);
    	for(int i = 0; i < size; i++) {
    		final var e   = Array.get(obj, i);
    		final var ide = file.append(e);
    		out.writeInt(ide);
    	}
	}

	@Override
	public Object read(Class<?> type, DataInput in, DataFileReader file) throws IOException {
		final var componentType = type.componentType();
		final var size = in.readInt();
		final var arr = (Object[]) Array.newInstance(componentType, size);
		for(int i = 0; i < size; i++) {
    		final var id = in.readInt();
			arr[i] = file.get(id);
		}
		return arr;
	}

}
