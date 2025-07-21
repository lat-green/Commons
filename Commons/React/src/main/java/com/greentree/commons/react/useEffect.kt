package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffect(dependency: Any, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(useDependency(dependency)) {
		block()
	}
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectByHash(dependency: Any, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(useDependencyByHash(dependency)) {
		block()
	}
}

@OptIn(ExperimentalContracts::class)
@Deprecated("", ReplaceWith("block()"))
inline fun ReactContext.useEffect(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	block()
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectClose(dependency: Any, block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	var closeablePrevious by useRefClose<AutoCloseable> {
		it.close()
	}
	if(useDependency(dependency)) {
		closeablePrevious = block()
	}
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectCloseByHash(dependency: Any, block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	var closeablePrevious by useRefClose<AutoCloseable> {
		it.close()
	}
	if(useDependencyByHash(dependency)) {
		closeablePrevious = block()
	}
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectClose(block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	var previousCloseable by useRefClose<AutoCloseable> {
		it.close()
	}
	previousCloseable = block()
}

