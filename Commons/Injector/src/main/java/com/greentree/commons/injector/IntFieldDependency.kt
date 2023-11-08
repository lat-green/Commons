package com.greentree.commons.injector

import com.greentree.commons.util.UnsafeUtil
import java.lang.reflect.Field

class IntFieldDependency(field: Field) : FieldDependency(field) {

	init {
		require(field.type == Int::class.java) { "field: $field is not int" }
	}

	override fun setValue(host: Any, value: Any) {
		val unsafe = UnsafeUtil.getUnsafeInstance()
		unsafe.putInt(host, offset, value as Int)
	}
}
