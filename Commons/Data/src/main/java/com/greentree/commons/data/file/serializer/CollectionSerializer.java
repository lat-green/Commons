
package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;
import com.greentree.commons.data.file.DataSerializer;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.cortege.Pair;
import com.greentree.commons.util.exception.WrappedException;

public class CollectionSerializer implements DataSerializer {

	@Override
	public TypeInfo<?> root() {
		return TypeInfoBuilder.getTypeInfo(Collection.class);
	}
	
	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		final var arr = (Collection<?>) obj;
		out.writeInt(arr.size());
		for(var c : arr) {
			out.writeInt(file.append(c));
		}
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@Override
	public Pair<Object, Runnable> read(TypeInfo<?> cls, DataInput in, DataFileReader file) throws IOException {
		var size = in.readInt();
		final var arr = new ArrayList<>(size);
		while(size-- > 0) {
			final var e = file.get(in.readInt());
			arr.add(e);
		}
		try {
			final var obj = (Collection) ClassUtil.newInstance(cls.toClass());
			return new Pair<>(obj, () -> {
				obj.addAll(arr);
			});
		}catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new WrappedException(e);
		}
	}

}
