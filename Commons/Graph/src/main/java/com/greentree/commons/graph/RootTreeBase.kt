package com.greentree.commons.graph

import java.io.Serializable
import java.util.*

class RootTreeBase<V>(root: V) : MutableRootTree<V> {

	private val infos: MutableMap<V, NodeInfo<V>> = HashMap()

	init {
		infos[root] = NodeInfo(root)
	}

	override fun add(vertex: V, parent: V) {
		require(infos.containsKey(parent)) { "parent: $parent not added" }
		val p_info = infos[parent]!!
		if(infos.containsKey(vertex)) {
			val info = infos[vertex]!!
			info.parent.remove(info)
			info.parent = p_info
			p_info.add(info)
		} else {
			val info = NodeInfo(p_info, vertex)
			infos[vertex] = info
		}
	}

	override fun remove(vertex: V) {
		val info = infos[vertex]!!
		require(!info.isRoot) { "remove root" }
		for(c in info) remove(c.value)
		info.parent.remove(info)
		infos.remove(vertex)
	}

	override fun clear() {
		val root = root
		infos.clear()
		infos[root] = NodeInfo(root)
	}

	override fun getParent(v: Any?): V {
		return infos[v]!!.parent.value
	}

	override fun getChildren(v: V): Sequence<V> {
		return infos[v]!!.asSequence().map { i -> i.value }
	}

	override fun contains(v: V): Boolean {
		return infos.containsKey(v)
	}

	override fun hashCode(): Int {
		return Objects.hash(infos)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj !is RootTreeBase<*>) return false
		return infos == obj.infos
	}

	override fun toString(): String {
		return "RootTreeBase $infos"
	}

	override fun iterator(): Iterator<V> {
		return infos.keys.iterator()
	}

	private class NodeInfo<V> : Iterable<NodeInfo<V>>, Serializable {

		val value: V
		var parent: NodeInfo<V>
		private var first: NodeInfo<V>? = null
		private var next: NodeInfo<V>? = null

		constructor(parent: NodeInfo<V>, value: V) {
			this.parent = parent
			this.value = value
			parent.add(this)
		}

		fun add(info: NodeInfo<V>?) {
			if(first == null) {
				first = info
				return
			}
			var n = first
			while(n != null) {
				if(n.next == null) {
					n.next = info
					return
				}
				n = n.next
			}
		}

		constructor(value: V) {
			this.parent = this
			this.value = value
		}

		override fun hashCode(): Int {
			return Objects.hash(first, next, parent, value)
		}

		override fun equals(obj: Any?): Boolean {
			if(this === obj) return true
			if(obj !is NodeInfo<*>) return false
			return first == obj.first && next == obj.next && value == obj.value
		}

		override fun toString(): String {
			val result = StringBuilder("[")
			result.append(value)
			if(first != null) result.append(", first=").append(first!!.value)
			if(next != null) result.append(", next=").append(next!!.value)
			result.append("]")
			return result.toString()
		}

		val isRoot: Boolean
			get() = this === parent

		override fun iterator(): Iterator<NodeInfo<V>> {
			return object : Iterator<NodeInfo<V>> {
				var n: NodeInfo<V>? = first

				override fun hasNext(): Boolean {
					return n != null
				}

				override fun next(): NodeInfo<V> {
					val result = n
					n = n!!.next
					return result!!
				}
			}
		}

		fun remove(info: NodeInfo<V>) {
			if(first == null) return
			if(first === info) {
				first = first!!.next
				return
			}
			var n = first
			while(n != null) {
				if(n.next === info) {
					n.next = n.next!!.next
					return
				}
				n = n.next
			}
		}

	}

}
