package com.greentree.data.file.serializer;

import static com.greentree.common.util.classes.ObjectBuilder.getMinConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.greentree.common.util.classes.ClassUtil;
import com.greentree.common.util.classes.info.TypeInfo;
import com.greentree.common.util.cortege.Pair;
import com.greentree.common.util.function.CheckedRunnable;
import com.greentree.data.externalizable.ObjectBuilderNew;
import com.greentree.data.file.DataFileReader;
import com.greentree.data.file.DataFileWriter;

public class FieldsSerializer extends AbstractSerializer {
	
	public FieldsSerializer() {
		super(Object.class);
	}
	
	@Override
	public int priority(TypeInfo<?> type) {
		try {
    		final var cls = type.toClass();
    		final var c = getMinConstructor(cls);
    		if(c == null) return Integer.MAX_VALUE;
    		if(c.getParameterCount() == 0)
    			return 10;
    		return 30;
		}catch (RuntimeException e) {
			throw new IllegalArgumentException(""+type, e);
		}
	}
	
	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		final var fs = new ArrayList<Field>();
		final var cls = obj.getClass();
		for(var f : ClassUtil.getAllNotStaticFields(cls)) {
			if(Modifier.isTransient(f.getModifiers()))
				continue;
			fs.add(f);
		}
		fs.sort(Comparator.comparing(f -> f.getName()));
		for(var f : fs) {
			try {
				final var e = ClassUtil.getField(obj, f);
				final var i = file.append(e); 
				out.writeInt(i);
			}catch(IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	@Override
	public Pair<Object, Runnable> read(TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException {
		final var cls = type.toClass();
		
		final var fs = new ArrayList<Field>();
		for(var f : ClassUtil.getAllNotStaticFields(cls)) {
			if(Modifier.isTransient(f.getModifiers()))
				continue;
			fs.add(f);
		}
		fs.sort(Comparator.comparing(f -> f.getName()));
		try {
			final var c = getMinConstructor(cls);
			if(c.getParameterCount() == 0) {
    			final var obj = c.newInstance();
    			return new Pair<>(obj, ((CheckedRunnable)() -> {
    				for(var f : fs) {
    					final var i = in.readInt();
    					final var e = file.get(i);
    					ClassUtil.setField(obj, f, e);
    				}
    			}).toNonCheked());
			}else {
				final var vals = new HashMap<String, Object>();
				for(var f : fs) {
					final var i = in.readInt();
					final var e = file.get(i);
					vals.put(f.getName(), e);
				}
				final var obj = ObjectBuilderNew.get(cls, vals.keySet(), t -> {
					return vals.get(t.v2);
				});
    			return new Pair<>(obj, () -> {
    			});
			}
		}catch(Exception e) {
			throw new RuntimeException(type+"", e);
		}
	}

	@Override
	protected Object read(Class<?> type, DataInput in, DataFileReader file) throws IOException {
		throw new UnsupportedOperationException(type + "");
	}

}
