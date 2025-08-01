package com.greentree.commons.event.property

import com.greentree.commons.event.observable.RunObservable

data class MapReactiveProperty<T : Any, R : Any>(
	val origin: ReactiveProperty<T>,
	val mapFunction: (T) -> R,
) : ReactiveProperty<R>, RunObservable by origin {

	override val value: R
		get() = mapFunction(origin.value)
}

fun <T : Any, R : Any> ReactiveProperty<T>.map(mapFunction: (T) -> R) = MapReactiveProperty(this, mapFunction)