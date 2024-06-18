package com.greentree.commons.graph.algorithm.cycle

import com.greentree.commons.graph.DirectedArc

data class JointCycle<out E : Any>(
	private val origin: List<DirectedArc<E>>,
) : List<DirectedArc<E>> by origin {

	@Deprecated("")
	fun cross(cycle: JointCycle<@UnsafeVariance E>): List<DirectedArc<E>> {
		val res: MutableList<DirectedArc<E>> = ArrayList()
		for(v in cycle) if(contains(v)) res.add(v)
		return res
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(javaClass != obj!!.javaClass) return false
		val other = obj as JointCycle<*>
		if(size != other.size) return false

		for(a in this) if(!other.contains(a)) return false

		return true
	}

	fun toVertex() = VertexCycle<E>(origin.map { it.begin })
}

fun <E : Any> JointCycle(cycle: List<E>) = JointCycle(
	sequence {
		for(i in 0 until cycle.size - 1)
			yield(DirectedArc(cycle[i], cycle[i + 1]))
		yield(DirectedArc(cycle[cycle.size - 1], cycle[0]))
	}.toList()
)
