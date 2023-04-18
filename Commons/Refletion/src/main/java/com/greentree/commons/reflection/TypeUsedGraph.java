package com.greentree.commons.reflection;

import java.util.HashSet;
import java.util.Iterator;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;

public final record TypeUsedGraph(Iterable<? extends TypeInfo<?>> classes) implements Graph<TypeInfo<?>> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Iterable<? extends TypeInfo<?>> getJoints(Object v) {
		var cls = (TypeInfo<?>) v;
		var result = new HashSet<TypeInfo<?>>();
		
		var superCls = cls.getSuperType();
		if(superCls != null)
			result.add(superCls);
		
		for(var i : cls.getInterfaces())
			result.add(i);
		
		for(var f : cls.toClass().getDeclaredFields())
			result.add(TypeInfoBuilder.getTypeInfo(f.getGenericType()));
		
		for(var method : cls.toClass().getDeclaredMethods()) {
			result.add(TypeInfoBuilder.getTypeInfo(method.getGenericReturnType()));
			for(var p : method.getParameters())
				result.add(TypeInfoBuilder.getTypeInfo(p.getParameterizedType()));
		}
		
		for(var constructor : cls.toClass().getDeclaredConstructors())
			for(var p : constructor.getParameters())
				result.add(TypeInfoBuilder.getTypeInfo(p.getParameterizedType()));
		
		result.remove(cls);
		return result.stream().filter(x -> x != null).filter(x -> !x.isPrimitive()).toList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<TypeInfo<?>> iterator() {
		return (Iterator<TypeInfo<?>>) classes.iterator();
	}
	
	
}
