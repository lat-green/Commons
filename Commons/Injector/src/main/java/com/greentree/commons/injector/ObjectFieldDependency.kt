package com.greentree.commons.injector

import com.greentree.commons.util.UnsafeUtil
import java.lang.reflect.Field

class ObjectFieldDependency(field: Field) : FieldDependency(field) {

	init {
		require(!field.type.isPrimitive) { "field: $field is primitive" }
	}

	override fun setValue(host: Any, value: Any) {
		val unsafe = UnsafeUtil.getUnsafeInstance()
		unsafe.putObject(host, offset, value)
	}
}
