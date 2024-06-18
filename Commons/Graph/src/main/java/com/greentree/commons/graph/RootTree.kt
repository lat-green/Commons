package com.greentree.commons.graph

import com.greentree.commons.graph.algorithm.lca.BruteLCAFinder

interface RootTree<out V : Any> : Tree<V> {

	fun containsInSubTree(v: @UnsafeVariance V, parent: @UnsafeVariance V): Boolean {
		for(e in getChildrenClosure(parent))
			if(v == e)
				return true
		return false
	}

	fun lca(a: @UnsafeVariance V, b: @UnsafeVariance V): V {
		val finder = BruteLCAFinder(this)
		return finder.lca(a, b)
	}

	val root: V
		get() {
			val iter = iterator()
			var p: V?
			var v: V?
			p = iter.next()
			do {
				v = p
				p = getParent(v)
			} while(v !== p)
			return v!!
		}

	/** @return parent of vertex, or root if vertex is root
	 */
	fun getParent(v: Any?): V

	fun depth(v: Any?): Int {
		var v = v
		var depth = 0
		var p: V? = null
		while(true) {
			p = getParent(v)
			if(p === v) return depth
			depth++
			v = p
		}
	}

	fun getChildrenClosure(v: @UnsafeVariance V): Iterable<V> {
		val result = ArrayList<V>()
		result.add(v)
		for(c in getChildren(v)) {
			for(e in getChildrenClosure(c)) result.add(e)
		}
		return result
	}

	override fun getAdjacencySequence(v: @UnsafeVariance V): Sequence<V> {
		return sequenceOf(getChildren(v), sequenceOf(getParent(v))).flatten()
	}

	fun getChildren(v: @UnsafeVariance V) = sequence {
		for(e in this@RootTree) {
			if(v == getParent(e))
				yield(e)
		}
	}
}
