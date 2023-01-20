package com.greentree.commons.util.collection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

@Deprecated
public class EmptySerialArrayList<E> implements Externalizable {
	
	private transient ArrayList<E> list;
	
	public EmptySerialArrayList() {
		this(new ArrayList<>());
	}
	
	public EmptySerialArrayList(ArrayList<E> list) {
		this.list = list;
	}
	
	public ArrayList<E> get() {
		return list;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		list = new ArrayList<>();
	}
	
	
	
	
}
