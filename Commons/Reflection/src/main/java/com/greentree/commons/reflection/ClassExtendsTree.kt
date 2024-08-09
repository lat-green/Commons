package com.greentree.commons.reflection

import com.greentree.commons.graph.RootTree

class ClassExtendsTree : RootTree<Class<*>> {

	override fun containsInSubTree(v: Class<*>, parent: Class<*>): Boolean {
		return ClassUtil.isExtends(parent, v)
	}

	override fun getParent(cls: Class<*>): Class<*> {
		if(cls === Any::class.java) return Any::class.java
		return cls.superclass
	}

	override fun iterator(): Iterator<Class<*>> {
		return ClassUtil.getAllClasses().filter { x: Class<*> -> x.superclass != null }.iterator()
	}
}
