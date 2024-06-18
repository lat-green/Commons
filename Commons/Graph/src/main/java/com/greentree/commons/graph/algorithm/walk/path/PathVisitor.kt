package com.greentree.commons.graph.algorithm.walk.path

fun interface PathVisitor<in V : Any> {

	fun visit(path: List<V>): Boolean
}