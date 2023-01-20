package com.greentree.common.util.collection;

public class TransientTable<K, V> extends Table<K, K, V> {

	@Override
	public V get(K row, K column) {
		if(row.hashCode() > column.hashCode())
			return super.get(row, column);
		else
			return super.get(column, row);
	}

	@Override
	public void put(K row, K column, V value) {
		if(row.hashCode() > column.hashCode())
			super.put(row, column, value);
		else
			super.put(column, row, value);
	}

}
