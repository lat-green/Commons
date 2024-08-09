package com.greentree.commons.reflection

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import java.util.*

data class TypeUsedFiniteGraph(
	val classes: Iterable<TypeInfo<*>>,
) : FiniteGraph<TypeInfo<*>> {

	override fun iterator(): Iterator<TypeInfo<*>> {
		return classes.iterator()
	}

	override fun getAdjacencySequence(cls: TypeInfo<*>): Sequence<TypeInfo<*>> {
		val result = HashSet<TypeInfo<*>>()
		val superCls = cls.superType
		if(superCls != null) result.add(superCls)
		Collections.addAll(result, *cls.interfaces)
		for(f in cls.toClass().declaredFields) result.add(getTypeInfo<Any>(f.genericType))
		for(method in cls.toClass().declaredMethods) {
			result.add(getTypeInfo<Any>(method.genericReturnType))
			for(p in method.parameters) result.add(getTypeInfo<Any>(p.parameterizedType))
		}
		for(constructor in cls.toClass().declaredConstructors) for(p in constructor.parameters) result.add(
			getTypeInfo<Any>(
				p.parameterizedType
			)
		)
		result.remove(cls)
		return result.toList().filter { x: TypeInfo<*> -> !x.isPrimitive }.asSequence()
	}
}
