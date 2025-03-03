package com.greentree.commons.injector

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.annotation.Annotations
import com.greentree.commons.annotation.filter
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Member
import java.lang.reflect.Parameter
import kotlin.reflect.KParameter

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@MustBeDocumented
@AnnotationInherited
annotation class Qualifier(
	val name: String,
)

val KParameter.qualifierOrName: String?
	get() = Annotations.filter(this).getAnnotation(Qualifier::class.java)?.name
val Parameter.qualifierOrName: String?
	get() = Annotations.filter(this).getAnnotation(Qualifier::class.java)?.name
val <T> T.qualifierOrName: String where T : AnnotatedElement, T : Member
	get() = Annotations.filter(this).getAnnotation(Qualifier::class.java)?.name ?: name