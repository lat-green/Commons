package com.greentree.commons.data.file;

public interface DataFileWriter {

	<T> int append(T last, T obj);
	int append(Object obj);
	
}
