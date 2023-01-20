package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;
import com.greentree.commons.util.exception.WrappedException;

public class MapSerializer extends AbstractSerializer {

	public MapSerializer() {
		super(Map.class);
	}

	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		final var map = (Map<?, ?>) obj;
		out.writeInt(map.size());
		for(var e : map.entrySet()) {
			out.writeInt(file.append(e.getKey()));
			out.writeInt(file.append(e.getValue()));
		}
	}

	@SuppressWarnings({"deprecation","unchecked"})
	@Override
	public Object read(Class<?> cls, DataInput in, DataFileReader file) throws IOException {
		final Map<Object, Object> map;
		try {
			map = (Map<Object, Object>) cls.newInstance();
		}catch(InstantiationException | IllegalAccessException e) {
			throw new WrappedException(e);
		}
		var size = in.readInt();
		while(size-- > 0) {
			final var k = file.get(in.readInt());
			final var v = file.get(in.readInt());
			map.put(k, v);
		}
		return map;
	}


}
