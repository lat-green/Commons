package com.greentree.commons.graph.algorithm.lca

import com.greentree.commons.graph.RootTree

class BruteLCAFinder<out V : Any>(
	private val tree: RootTree<V>,
) : LCAFinder<V> {

	override fun lca(a: @UnsafeVariance V, b: @UnsafeVariance V): V {
		if(tree.containsInSubTree(a, b)) return b
		return lca(a, tree.getParent(b))
	}
}
