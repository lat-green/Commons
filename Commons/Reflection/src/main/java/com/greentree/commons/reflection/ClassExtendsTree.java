package com.greentree.commons.reflection;

import java.util.Iterator;

import com.greentree.commons.graph.RootTree;

public final class ClassExtendsTree implements RootTree<Class<?>> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean containsInSubTree(Object v, Object parent) {
		return ClassUtil.isExtends((Class<?>) parent, (Class<?>) v);
	}
	
	@Override
	public Class<?> getParent(Object cls) {
		if(cls == Object.class)
			return Object.class;
		return ((Class<?>) cls).getSuperclass();
	}
	
	@Override
	public Iterator<Class<?>> iterator() {
		return ClassUtil.getAllClasses().filter(x -> x.getSuperclass() != null).iterator();
	}
	
}
