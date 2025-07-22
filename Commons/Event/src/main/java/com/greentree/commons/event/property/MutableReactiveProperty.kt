package com.greentree.commons.event.property

interface MutableReactiveProperty<T : Any> : ReactiveProperty<T> {

	override var value: T
}