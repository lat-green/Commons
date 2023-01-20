package com.greentree.data.file;

public interface DataFileWriter {

	<T> int append(T last, T obj);
	int append(Object obj);
	
}
