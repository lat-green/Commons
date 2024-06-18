package com.greentree.commons.graph.algorithm.walk.path

import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

data class VertexToPathVisitor<in V : Any>(
	val origin: VertexVisitor<V>,
) : PathVisitor<V> {

	override fun visit(path: List<V>): Boolean {
		val path = path.iterator()
		if(!path.hasNext())
			return true
		var result = true
		val v0 = path.next()
		if(origin.startVisit(v0)) {
			var p = v0
			var running = true
			while(running && path.hasNext()) {
				val v = path.next()
				if(!origin.startVisit(p, v)) {
					running = false
					result = false
				}
				origin.endVisit(p, v)
			}
		}
		origin.endVisit(v0)
		return result
	}
}