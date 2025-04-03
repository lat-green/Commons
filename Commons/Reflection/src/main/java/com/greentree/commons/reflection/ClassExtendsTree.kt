package com.greentree.commons.reflection

import com.greentree.commons.graph.RootTree
import com.greentree.commons.reflection.info.TypeUtil

class ClassExtendsTree : RootTree<Class<*>> {

	override fun containsInSubTree(v: Class<*>, parent: Class<*>): Boolean {
		return TypeUtil.isExtends(parent, v)
	}

	override fun getParent(cls: Class<*>): Class<*> {
		if(cls === Any::class.java) return Any::class.java
		return cls.superclass
	}

	override fun iterator(): Iterator<Class<*>> {
		TODO("iterator")
	}
}
