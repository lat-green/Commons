package com.greentree.commons.data.file;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.cortege.Pair;

public interface DataSerializer {
	
	TypeInfo<?> root();
	
	void write(Object obj, DataOutput out, DataFileWriter file) throws IOException;
	Pair<Object, Runnable> read(TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException;

	default <T> void write(T last, T obj, DataOutput out, DataFileWriter file) throws IOException {
		write(obj, out, file);
	}
	
	default Pair<Object, Runnable> read(Object last, TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException {
		return read(type, in, file);
	}

	static byte[] realloc(byte[] arr, int dlen) {
		assert dlen > 0;
		final var res = new byte[arr.length + dlen];
		for(int i = 0; i < arr.length; i++)
			res[i] = arr[i];
		return res;
	}
	
	default int priority(TypeInfo<?> type) {
		return 0;
	}
	
}
