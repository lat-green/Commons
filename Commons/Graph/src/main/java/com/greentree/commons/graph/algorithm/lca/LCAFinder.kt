package com.greentree.commons.graph.algorithm.lca

interface LCAFinder<out V : Any> {

	fun lca(a: @UnsafeVariance V, b: @UnsafeVariance V): V
}
