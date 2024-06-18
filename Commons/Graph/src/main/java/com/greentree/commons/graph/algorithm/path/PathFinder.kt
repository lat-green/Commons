package com.greentree.commons.graph.algorithm.path

interface PathFinder<out V: Any> {

	fun getPath(begin: @UnsafeVariance V, end: @UnsafeVariance V): VertexPath<V>
}
