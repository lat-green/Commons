package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

data class OneUseWalker<out V : Any>(
	val origin: GraphWalker<V>,
) : GraphWalker<V> by origin {

	override fun visit(visitor: VertexVisitor<V>) {
		origin.visit(visitor.asOneUse())
	}

	override fun visit(start: @UnsafeVariance V, visitor: VertexVisitor<V>) {
		origin.visit(start, visitor.asOneUse())
	}

	override fun visit(start: @UnsafeVariance V, visitor: PathVisitor<V>) {
		origin.visit(start, visitor.asOneUse())
	}

	class OneUsePathVisitor<V : Any>(
		val visitor: PathVisitor<V>,
	) : PathVisitor<V> {

		private val used: MutableSet<V> = mutableSetOf()

		override fun visit(path: List<V>): Boolean {
			val v = path.last()
			if(v in used)
				return false
			used.add(v)
			return visitor.visit(path)
		}
	}

	class OneUseVertexVisitor<V : Any>(
		val visitor: VertexVisitor<V>,
	) : VertexVisitor<V> {

		private val used: MutableSet<V> = mutableSetOf()

		override fun endVisit(v: V) {
			used.add(v)
			visitor.endVisit(v)
		}

		override fun endVisit(parent: V, v: V) {
			used.add(v)
			visitor.endVisit(parent, v)
		}

		override fun startVisit(v: V): Boolean {
			if(used.contains(v))
				return false
			return visitor.startVisit(v)
		}

		override fun startVisit(parent: V, v: V): Boolean {
			if(used.contains(v))
				return false
			return visitor.startVisit(parent, v)
		}
	}
}

fun <V : Any> VertexVisitor<V>.asOneUse() = OneUseWalker.OneUseVertexVisitor(this)
fun <V : Any> PathVisitor<V>.asOneUse() = OneUseWalker.OneUsePathVisitor(this)

fun <V : Any> GraphWalker<V>.asOneUse() = OneUseWalker(this)

