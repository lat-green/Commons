package com.greentree.common.graph

import com.greentree.commons.graph.RootTree
import com.greentree.commons.graph.RootTreeBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

@Disabled
class LcaTest {

	@Test
	fun test1() {
		val tree = RootTreeBase("A")
		tree.add("B", "A")
		tree.add("C", "A")
		tree.add("D", "B")
		tree.add("E", "B")
		val a = tree.lca("D", "C")
		Assertions.assertEquals(a, "A")
	}

	@Test
	fun classTree() {
		val tree: RootTree<Class<*>> = object : RootTree<Class<*>> {
			override fun containsInSubTree(cls: Class<*>, superClass: Class<*>): Boolean {
				return superClass.isAssignableFrom(cls)
			}

			override fun getParent(v: Class<*>): Class<*> {
				if(v === Any::class.java) return Any::class.java
				return (v as Class<*>?)!!.superclass
			}

			override fun iterator(): Iterator<Class<*>> {
				TODO("Not yet implemented")
			}
		}
		val a = tree.lca(ArrayList::class.java, LinkedList::class.java)
		Assertions.assertEquals(a, AbstractList::class.java)
	}
}
