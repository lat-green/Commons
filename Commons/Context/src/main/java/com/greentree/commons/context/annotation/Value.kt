package com.greentree.commons.context.annotation

import com.greentree.commons.annotation.AnnotationInherited

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@MustBeDocumented
@AnnotationInherited
annotation class Value(
	val name: String,
	val default: String = DEFAULT_DEFAULT,
) {

	companion object {

		const val DEFAULT_DEFAULT = ""
	}
}