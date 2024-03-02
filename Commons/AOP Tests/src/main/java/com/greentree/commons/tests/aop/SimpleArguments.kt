package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments

data class SimpleArguments(var array: Array<Any>) : Arguments {

	override fun get(): Array<Any> = array
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as SimpleArguments

		return array.contentEquals(other.array)
	}

	override fun hashCode(): Int {
		return array.contentHashCode()
	}
}
