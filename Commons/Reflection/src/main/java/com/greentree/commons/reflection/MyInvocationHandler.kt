package com.greentree.commons.reflection

interface MyInvocationHandler {

	fun invoke(thisRef: Any, methodName: String): Any?
}