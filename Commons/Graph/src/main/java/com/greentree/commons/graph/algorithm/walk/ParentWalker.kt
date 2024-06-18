package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.RootTree
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class ParentWalker<V : Any>(private val tree: RootTree<V>) :
	RootTreeWalker<V> {

	override fun visit(start: V, visitor: VertexVisitor<V>) {
		walk(visitor, start)
	}

	override fun graph(): RootTree<V> {
		return tree
	}

	override fun visit(visitor: VertexVisitor<V>) {
		super.visit(visitor)
	}

	private fun walk(visitor: VertexVisitor<V>, v: V) {
		visitor.startVisit(v)
		val to = tree.getParent(v)
		walk(visitor, to, v)
		visitor.endVisit(v)
	}

	private fun walk(visitor: VertexVisitor<V>, v: V, parent: V) {
		visitor.startVisit(parent, v)
		val to = tree.getParent(v)
		walk(visitor, to, v)
		visitor.endVisit(parent, v)
	}
}
