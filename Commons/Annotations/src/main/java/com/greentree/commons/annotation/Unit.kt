package com.greentree.commons.annotation

fun <T, R> tree(root: T, block: (T, (T) -> R) -> R) = object : (T) -> R {
	override fun invoke(root: T) = block(root, this)
}(root)