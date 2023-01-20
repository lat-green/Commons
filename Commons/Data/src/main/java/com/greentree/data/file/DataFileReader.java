package com.greentree.data.file;

public interface DataFileReader {

	<T> T get(T last, int id);
	Object get(int id);
	
}
