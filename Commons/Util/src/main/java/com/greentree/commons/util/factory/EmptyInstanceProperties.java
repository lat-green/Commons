package com.greentree.commons.util.factory;

import com.greentree.commons.util.iterator.EmptyIterable;

public class EmptyInstanceProperties implements InstanceProperties {
	
	@Override
	public Iterable<? extends String> names() {
		return EmptyIterable.instance();
	}
	
	@Override
	public InstanceProperty get(String name) {
		return null;
	}
	
}
