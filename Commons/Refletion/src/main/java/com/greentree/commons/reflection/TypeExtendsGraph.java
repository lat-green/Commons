package com.greentree.commons.reflection;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.greentree.commons.graph.Graph;

public record TypeExtendsGraph(Iterable<Class<?>> classes) implements Graph<Class<?>> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Iterable<? extends Class<?>> getJoints(Object v) {
		var cls = (Class<?>) v;
		var result = StreamSupport.stream(spliterator(), false)
				.filter(x ->
				{
					if(x.equals(cls.getSuperclass()) || cls.equals(x.getSuperclass()))
						return true;
					for(var i : x.getInterfaces())
						if(i.equals(cls))
							return true;
					for(var i : cls.getInterfaces())
						if(i.equals(x))
							return true;
					return false;
				}).toList();
		return result;
	}
	
	@Override
	public Iterator<Class<?>> iterator() {
		return classes.iterator();
	}
	
	
}
