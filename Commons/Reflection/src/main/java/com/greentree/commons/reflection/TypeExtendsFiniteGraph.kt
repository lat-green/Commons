package com.greentree.commons.reflection

import com.greentree.commons.graph.FiniteGraph

data class TypeExtendsFiniteGraph(val classes: Iterable<Class<*>>) : FiniteGraph<Class<*>> {

	private fun isAdjacency(cls: Class<*>, parent: Class<*>): Boolean {
		if(cls == parent.superclass || parent == cls.superclass)
			return true
		for(i in cls.interfaces)
			if(i == parent) return true
		for(i in parent.interfaces)
			if(i == cls) return true
		return false
	}

	override fun getAdjacencySequence(cls: Class<*>) = asSequence().filter { isAdjacency(it, cls) }

	override fun iterator(): Iterator<Class<*>> {
		return classes.iterator()
	}
}
