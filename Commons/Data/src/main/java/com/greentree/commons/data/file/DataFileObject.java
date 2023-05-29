package com.greentree.commons.data.file;

import java.io.Serializable;
import java.util.Arrays;

import com.greentree.commons.reflection.info.TypeInfo;


public record DataFileObject(TypeInfo<?> type, byte[] data) implements Serializable {

	@Override
	public String toString() {
		return "AssetFileObject ["+ type +"]" + Arrays.toString(data);
	}
	
	public int size() {
		return data.length;
	}
	
}
