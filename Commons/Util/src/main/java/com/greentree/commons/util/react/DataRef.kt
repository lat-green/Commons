package com.greentree.commons.util.react

data class DataRef<T>(
	override var value: T,
) : Ref<T>