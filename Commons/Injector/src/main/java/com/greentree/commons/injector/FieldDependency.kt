package com.greentree.commons.injector

import com.greentree.commons.util.UnsafeUtil
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

abstract class FieldDependency(val field: Field) : Dependency {

	override fun toString(): String {
		return "FieldDependency [$field]"
	}

	val offset = UnsafeUtil.getUnsafeInstance().objectFieldOffset(field)

	private fun valueOptional(container: InjectionContainer): Optional<*> {
		return container[field.name, field.type]
	}

	override fun set(host: Any, container: InjectionContainer) {
		try {
			val optional = valueOptional(container)
			require(!optional.isEmpty) { "not found value to $field to host:$host" }
			setValue(host, optional.get())
		} catch(e: IllegalArgumentException) {
			throw RuntimeException(e)
		} catch(e: IllegalAccessException) {
			throw RuntimeException(e)
		}
	}

	abstract fun setValue(host: Any, value: Any)

	init {
		Objects.requireNonNull(field)
		val mod = field.modifiers
		require(!Modifier.isStatic(mod)) { "static field" }
		require(!Modifier.isFinal(mod)) { "final field" }
	}
}
