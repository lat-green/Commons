package com.greentree.commons.util

import kotlin.reflect.KProperty

interface Cache<R> {

	operator fun getValue(
		thisRef: Any,
		property: KProperty<*>,
	): R
}

private fun <R> useCache(target: Any, function: () -> R): Cache<R> {
	return object : Cache<R> {
		private var hashCode = target.hashCode()
		private var cache = function()

		override fun getValue(thisRef: Any, property: KProperty<*>): R {
			val hc = target.hashCode()
			if(hashCode != hc) {
				hashCode = hc
				cache = function()
			}
			return cache
		}
	}
}