package com.greentree.common.util.collection;

import java.util.HashMap;
import java.util.Map;

public class Table<R, C, V> {

	private final Map<R, Map<C, V>> map = new HashMap<>();

	public final boolean contains(R row, C column) {
		return get(row, column) != null;
	}

	public V get(R row, C column) {
		return getRow(row).get(column);
	}

	public Map<C, V> getRow(R row) {
		var res = map.get(row);
		if(res == null) {
			res = new HashMap<>();
			map.put(row, res);
		}
		return res;
	}

	public void put(R row, C column, V value) {
		getRow(row).put(column, value);
	}

}
