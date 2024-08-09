package com.greentree.commons.graph

import kotlin.experimental.ExperimentalTypeInference

class GeneratedRootTree<out V : Any>(
	override val root: V,
	val children: (@UnsafeVariance V) -> Sequence<V>,
) : RootTree<V> {

	override fun iterator(): Iterator<V> {
		return getChildrenClosure(root).iterator()
	}

	override fun getParent(v: @UnsafeVariance V): V {
		for(parent in this) {
			if(v in getChildren(parent))
				return parent
		}
		return v
	}

	override fun getChildren(v: @UnsafeVariance V) = children(v)
}

fun <V : Any> generateTree(root: V, children: (V) -> Sequence<V>) = GeneratedRootTree(root, children)

@OptIn(ExperimentalTypeInference::class)
fun <V : Any> tree(root: V, @BuilderInference children: suspend SequenceScope<V>.(V) -> Unit) =
	generateTree(root) { v ->
		sequence {
			children(v)
		}
	}.toList()