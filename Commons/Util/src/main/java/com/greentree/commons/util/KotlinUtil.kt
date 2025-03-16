package com.greentree.commons.util

import java.util.*

inline fun repeat(times: Long, action: (Long) -> Unit) {
	for(index in 0L until times) {
		action(index)
	}
}

fun <V : Any> traverseSequence(root: V, children: (V) -> Sequence<V>): Sequence<V> {
	val result = mutableSetOf<V>()
	val toAdd: Queue<V> = LinkedList()
	return sequence {
		result.clear()
		toAdd.clear()
		toAdd.add(root)
		while(toAdd.isNotEmpty()) {
			val e = toAdd.remove()
			result.add(e)
			yield(e)
			children(e).forEach { to ->
				if(to !in result && to !in toAdd) {
					toAdd.add(to)
				}
			}
		}
	}
}

fun <V : Any> traverse(root: V, children: SequenceScope<V>.(V) -> Unit): Sequence<V> =
	traverseSequence(root) { sequence { children(it) } }

inline fun <T, R> Iterable<T>.mapOption(block: (T) -> Result<R>): Result<List<R>> {
	val iter = iterator()
	val result = mutableListOf<R>()
	while(iter.hasNext()) {
		val opt = block(iter.next())
		if(opt.isFailure)
			return opt as Result<List<R>>
		result.add(opt.getOrThrow())
	}
	return Result.success(result)
}