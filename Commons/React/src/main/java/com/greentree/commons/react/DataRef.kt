package com.greentree.commons.react

data class DataRef<T>(
	override var value: T,
) : Ref<T>