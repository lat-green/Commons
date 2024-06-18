package com.greentree.commons.graph.algorithm.path

class GraphPathBaseDistance<V> private constructor() : (V, V) -> Number {

	companion object {

		private val INSTANCE = GraphPathBaseDistance<Any>()

		fun <V> instance() = INSTANCE as GraphPathBaseDistance<V>
	}
	//	@JvmField
//	val BASE_DISTANCE: (Any, Any, out Number> = BiFunction<Any, Any, Number> { a: Any?, b: Any? -> 1 }
	override fun invoke(p1: V, p2: V) = 1f
}
