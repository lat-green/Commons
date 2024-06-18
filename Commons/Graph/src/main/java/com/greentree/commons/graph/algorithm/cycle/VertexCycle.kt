package com.greentree.commons.graph.algorithm.cycle

data class VertexCycle<out E : Any>(
	private val origin: List<E>,
) : List<E> by origin {

	private fun cycleEquals(other: VertexCycle<*>): Boolean {
		val offset = other.indexOf(get(0))
		if(offset == -1) return false
		val size = size
		if(size != other.size) return false

		for(i in 0 until size) if(get(i) != other[(i + offset) % size]) return false

		return true
	}

	private fun cycleEqualsInverse(other: VertexCycle<*>): Boolean {
		val offset = other.indexOf(get(0))
		if(offset == -1) return false
		val size = size
		if(size != other.size) return false

		for(i in 0 until size) if(get(i) != other[(size - i + offset) % size]) return false

		return true
	}

	override fun equals(other: Any?): Boolean {
		return other is VertexCycle<*> && (cycleEquals(other) || cycleEqualsInverse(other))
	}

	override fun hashCode(): Int {
		var hash = 31
		for(a in this) hash += a.hashCode()
		return hash
	}

	fun toJoints(): JointCycle<E> {
		return JointCycle(this)
	}
}

