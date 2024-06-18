package com.greentree.commons.graph.algorithm.walk.vertex

fun interface VertexVisitor<in V : Any> {

	fun endVisit(v: V) {
	}

	fun endVisit(parent: V, v: V) {
		endVisit(v)
	}

	fun startVisit(v: V): Boolean

	fun startVisit(parent: V, v: V): Boolean {
		return startVisit(v)
	}
}

fun <V : Any> VertexVisitor<V>.visitPath(path: Iterator<V>) {
	val v0 = path.next()
	if(startVisit(v0)) {
		var p = v0
		var visit = true
		while(visit && path.hasNext()) {
			val v = path.next()
			visit = startVisit(p, v)
			endVisit(p, v)
			p = v
		}
	}
	endVisit(v0)
}