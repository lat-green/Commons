package com.greentree.commons.context.mock

interface A {

	val b: B
}

interface B {

	val a: A
}

data class AImpl(override val b: B) : A {

	override fun toString(): String {
		return "A"
	}
}

data class BImpl(override val a: A) : B