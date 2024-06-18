package com.greentree.commons.graph

import java.io.Serializable

data class DirectedArc<out E : Any>(
	val begin: E,
	val end: E,
) : Serializable {

	init {
		require(begin != end) { "DirectedArc a==b ($begin)" }
	}

	override fun toString(): String {
		return "($begin $end)"
	}
}
