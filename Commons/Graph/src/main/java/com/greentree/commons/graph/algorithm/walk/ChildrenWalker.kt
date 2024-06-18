package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.RootTree
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class ChildrenWalker<V : Any>(private val tree: RootTree<V>) :
	RootTreeWalker<V> {

	override fun visit(start: V, visitor: VertexVisitor<V>) {
		walk(visitor, start)
	}

	override fun graph(): RootTree<V> {
		return tree
	}

	private fun walk(visitor: VertexVisitor<V>, v: V) {
		visitor.startVisit(v)
		for(to in tree.getChildren(v)) walk(visitor, to, v)
		visitor.endVisit(v)
	}

	private fun walk(visitor: VertexVisitor<V>, v: V, parent: V) {
		visitor.startVisit(parent, v)
		for(to in tree.getChildren(v)) walk(visitor, to, v)
		visitor.endVisit(parent, v)
	}
}
