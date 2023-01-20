package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.ObjectBuilder;
import com.greentree.commons.util.exception.WrappedException;

public class RecordSerializer extends AbstractSerializer {

	
	public RecordSerializer() {
		super(Record.class);
	}
	
	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		try {
    		final var cls = obj.getClass();
    		for(var f : cls.getDeclaredFields()) {
    			final var e = ClassUtil.getField(obj, f);
    			final var i = file.append(e);
    			out.writeInt(i);
    		}
		}catch(IllegalArgumentException | IllegalAccessException e) {
			throw new WrappedException(e);
		}
	}

	@Override
	protected Object read(Class<?> cls, DataInput in, DataFileReader file) throws IOException {
		try {
			final var fs = cls.getDeclaredFields();
			final var vals = new Object[fs.length];
    		for(int i = 0; i < fs.length; i++) {
    			final var id = in.readInt();
    			final var e  = file.get(id);
				vals[i] = e;
    		}
    		final var c = ObjectBuilder.getMinConstructor(cls);
    		return c.newInstance(vals);
		}catch(IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new WrappedException(e);
		}
	}



}
