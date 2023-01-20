package com.greentree.data.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.greentree.common.util.collection.MapID;
import com.greentree.common.util.collection.MapIDImpl;
import com.greentree.common.util.iterator.SizedIterable;

public class DataFile implements DataFileReader, DataFileWriter, SizedIterable<DataFileObject>, Serializable {
	private static final long serialVersionUID = 1L;

	private final MapID gids = new MapIDImpl();

	{
		gids.get();// remove NULL==0
	}

	private Map<Integer, DataFileObject> objects = new HashMap<>();
	@Override
	public int append(Object obj) {
		return writer().append(obj);
	}

	@Override
	public <T> int append(T last, T obj) {
		if(obj == null) return DataFiles.NULL;
		return add(DataFiles.write(last, obj, this));
	}

	@Override
	public Object get(int id) {
		return reader().get(id);
	}

	@Override
	public <T> T get(T last, int id) {
		final var p = DataFiles.get(last, getAssetFilObject(id), this);
		p.seconde.run();
		return p.first;
	}

	@Override
	public Iterator<DataFileObject> iterator() {
		return objects.values().iterator();
	}

	@Override
	public String toString() {
		return "AssetFile " + objects;
	}

	private int add(DataFileObject faobj) {
		for(var e : objects.entrySet())
			if(e.getValue().equals(faobj))
				return e.getKey();
		final var id = gids.get();
		objects.put(id, faobj);
		return id;
	}

	private DataFileObject getAssetFilObject(int id) {
		final var faobj = objects.get(id);
		if(faobj != null)
			return faobj;
		throw new IllegalArgumentException("not found " + id + " in " + this);
	}

	private DataFile.Reader reader() {
		return new Reader();
	}

	private DataFile.Writer writer() {
		return new Writer();
	}

	private final class Reader implements DataFileReader {

		private final Map<Integer, Object> objs = new HashMap<>();
		{
			objs.put(0, null);
		}

		@Override
		public Object get(int id) {
			if(objs.containsKey(id))
				return objs.get(id);
			final var p = DataFiles.get(getAssetFilObject(id), this);
			final var obj = p.first;
			objs.put(id, obj);
			p.seconde.run();
			return obj;
		}

		@Override
		public <T> T get(T last, int id) {
			return DataFile.this.get(last, id);
		}

	}

	private final class Writer implements DataFileWriter {

		private final Map<Object, Integer> objs = new HashMap<>();
		{
			objs.put(null, 0);
		}

		@Override
		public int append(Object obj) {
			if(objs.containsKey(obj))
				return objs.get(obj);
			final var id = gids.get();
			objs .put(obj, id);
			final var faobj = DataFiles.write(obj, this);
			for(var e : objects.entrySet())
				if(e.getValue().equals(faobj)) {
					gids.free(id);
					objs.remove(obj);
					return e.getKey();
				}
			objects.put(id, faobj);
			return id;
		}

		@Override
		public <T> int append(T last, T obj) {
			return DataFile.this.append(last, obj);
		}

	}

	@Override
	public int size() {
		return objects.size();
	}

}
