package com.greentree.commons.data.file;

public interface DataFileReader {

	<T> T get(T last, int id);
	Object get(int id);
	
}
