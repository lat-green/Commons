package com.greentree.commons.util.react

fun ReactContext.useDependency(dependency: Any): Boolean {
	var previous by useRef<Any>()
	if(previous != dependency) {
		previous = dependency
		return true
	}
	return false
}

fun ReactContext.useDependencyByHash(dependency: Any): Boolean {
	var previous by useRef<Any>()
	val hash = dependency.hashCode()
	if(previous != hash) {
		previous = hash
		return true
	}
	return false
}
