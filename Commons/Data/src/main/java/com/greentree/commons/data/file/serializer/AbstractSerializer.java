package com.greentree.commons.data.file.serializer;

import java.io.DataInput;
import java.io.IOException;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataSerializer;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.util.cortege.Pair;


public abstract class AbstractSerializer implements DataSerializer {

	private final TypeInfo<?> root;

	public AbstractSerializer(Class<?> root) {
		this(TypeInfoBuilder.getTypeInfo(root));
	}
	
	public AbstractSerializer(TypeInfo<?> root) {
		this.root = root;
	}

	@Override
	public TypeInfo<?> root() {
		return root;
	}

	@Override
	public Pair<Object, Runnable> read(Object last, TypeInfo<?> type, DataInput in, DataFileReader file) throws IOException {
		final var obj = read(last, type.toClass(), in, file);
		return new Pair<>(obj, () -> {
		});
	}

	public Object read(Object last, Class<?> cls, DataInput in, DataFileReader file) throws IOException {
		final var obj = read(TypeInfoBuilder.getTypeInfo(cls), in, file);
		obj.seconde.run();
		return obj.first;
	}

	@Override
	public Pair<Object, Runnable> read(TypeInfo<?> cls, DataInput in, DataFileReader file) throws IOException {
		final var obj = read(cls.toClass(), in, file);
		return new Pair<>(obj, () -> {
		});
	}
	
	protected abstract Object read(Class<?> cls, DataInput in, DataFileReader file) throws IOException;
	
}
