package com.greentree.commons.graph

import java.util.*

open class MultiFiniteGraph<V : Any> : MutableFiniteGraph<V> {

	@JvmField
	protected val all_arcs: MutableMap<V, MutableList<V>> = HashMap()
	protected val arcs: MutableMap<V, MutableSet<V>> = HashMap()

	constructor()

	constructor(graph: FiniteGraph<V>) {
		addAll(graph)
	}

	override fun getAdjacencySequence(v: V): Sequence<V> {
		if(all_arcs.containsKey(v))
			return all_arcs[v]!!.asSequence()
		throw IllegalArgumentException("not vertex v:$v")
	}

	override fun has(from: V, to: V): Boolean {
		if(!contains(from)) return false
		return arcs[from]!!.contains(to)
	}

	override fun contains(v: V): Boolean {
		return all_arcs.containsKey(v)
	}

	override fun clear() {
		all_arcs.clear()
		arcs.clear()
	}

	override fun remove(v: V): Boolean {
		if(!contains(v)) return false
		all_arcs[v]!!.clear()
		all_arcs.remove(v)
		arcs[v]!!.clear()
		arcs.remove(v)
		for(b in all_arcs.values) b.remove(v)
		for(b in arcs.values) b.remove(v)
		return true
	}

	override fun remove(from: V, to: V): Boolean {
		if(!contains(from)) return false
		val tos = all_arcs[from]!!
		if(tos.remove(to)) {
			if(!tos.contains(to)) arcs[from]!!.remove(to)
			return true
		}
		return false
	}

	override fun add(v: V) {
		if(contains(v)) return
		all_arcs[v] = ArrayList()
		arcs[v] = HashSet()
	}

	override fun add(from: V, to: V): Boolean {
		add(from)
		add(to)
		arcs[from]!!.add(to)
		all_arcs[from]!!.add(to)
		return true
	}

	override fun hashCode(): Int {
		return Objects.hash(all_arcs)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj !is MultiFiniteGraph<*>) return false
		return all_arcs == obj.all_arcs
	}

	override fun toString(): String {
		return "MultiGraph [$all_arcs]"
	}

	override fun iterator(): Iterator<V> {
		return all_arcs.keys.iterator()
	}
}
