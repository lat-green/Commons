package com.greentree.commons.action.property

interface MutableReactiveProperty<T : Any> : ReactiveProperty<T> {

	override var value: T
}