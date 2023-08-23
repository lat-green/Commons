package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments

data class SimpleArguments(var array: Array<Any>) : Arguments {

	override fun get(): Array<Any> = array
}
