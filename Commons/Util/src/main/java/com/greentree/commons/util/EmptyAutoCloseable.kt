package com.greentree.commons.util

data object EmptyAutoCloseable : AutoCloseable {

	override fun close() {
	}
}