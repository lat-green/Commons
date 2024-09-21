package com.greentree.commons.graph

import java.util.*
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

inline fun <V : Any> treeSequence(root: V, children: (V) -> Sequence<V>): List<V> {
	val result = mutableListOf<V>()
	val toAdd: Queue<V> = LinkedList()
	toAdd.add(root)
	while(toAdd.isNotEmpty()) {
		val e = toAdd.remove()
		children(e).forEach { to ->
			if(to !in result) {
				toAdd.add(e)
				result.add(e)
			}
		}
	}
	return result
}

@OptIn(ExperimentalTypeInference::class)
fun <V : Any> tree(root: V, @BuilderInference children: suspend SequenceScope<V>.(V) -> Unit) =
	treeSequence(root) { v ->
		sequence {
			children(v)
		}
	}

inline fun <R : Any, V : R> treeWithOutRootSequence(root: R, crossinline children: (R) -> Sequence<V>): List<V> {
	return children(root).flatMap { treeSequence(it, children) }.toList()
}

@OptIn(ExperimentalTypeInference::class)
fun <R : Any, V : R> treeWithOutRoot(root: R, @BuilderInference children: suspend SequenceScope<V>.(R) -> Unit) =
	treeWithOutRootSequence(root) {
		sequence {
			children(it)
		}
	}