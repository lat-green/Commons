package com.greentree.commons.util.react

import kotlin.reflect.KProperty

interface Ref<T> {

	var value: T

	operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		this.value = value
	}
}

class DataRef<T>(
	override var value: T,
	val onClose: (T) -> Unit,
) : Ref<T>, AutoCloseable {

	//	init {
//		Cleaner.create().register(this, CleanRefRunnable(this, onClose))
//	}
//
//	data class CleanRefRunnable<T>(
//		val ref: WeakReference<Ref<T>>,
//		val onClose: (T) -> Unit,
//	) : Runnable {
//
//		constructor(
//			ref: Ref<T>,
//			onClose: (T) -> Unit,
//		) : this(WeakReference(ref), onClose)
//
//		override fun run() {
//			ref.get()!!.let {
//				onClose(it.value)
//			}
//		}
//	}
	override fun close() {
		onClose(value)
	}
}
