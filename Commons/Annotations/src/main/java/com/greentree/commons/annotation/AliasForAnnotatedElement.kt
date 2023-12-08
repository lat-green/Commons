package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Proxy

class AliasForAnnotatedElement(
	val origin: AnnotatedElement,
) : AnnotatedElement {

	override fun <A : Annotation> getAnnotation(annotationClass: Class<A>): A? {
		for(a in origin.declaredAnnotations) {
			if(a.annotationClass == annotationClass)
				return a as A
			val annotations = tree(a) { e, chain: (Annotation) -> List<Annotation>? ->
				if(e.annotationClass.java == annotationClass)
					return@tree listOf(e)
				for(a in e.annotationClass.java.declaredAnnotations) {
					if(a.annotationClass.java.isAnnotationPresent(AnnotationInherited::class.java)) {
						val result = chain(a)
						if(result != null)
							return@tree result + listOf(e)
					}
				}
				null
			}
			if(annotations != null) {
				val values = mutableMapOf<String, Any?>()
				for(m in annotationClass.declaredMethods)
					values[m.name] = m.defaultValue
				for(m in annotationClass.declaredMethods) {
					values[m.name] = m.invoke(annotations[0])
				}
				for(i in 1 ..< annotations.size) {
					val a = annotations[i]
					for(method in a.getAlias(annotationClass)) {
						val value = method.invoke(a)
						values[method.name] = value
					}
				}
				return annotationForMap(annotationClass, values)
			}
		}
		return null
	}

	private fun <A : Annotation?> annotationForMap(type: Class<A>, memberValues: Map<String, Any?>): A {
		return Proxy.newProxyInstance(
			type.classLoader,
			arrayOf(type),
			AnnotationInvocationHandler(type, memberValues)
		) as A
	}

	override fun getAnnotations() = getDeclaredAnnotations()

	override fun getDeclaredAnnotations() =
		origin.declaredAnnotations.mapNotNull { getAnnotation(it.annotationClass.java) }.toTypedArray()
//	override fun getDeclaredAnnotations() = origin.declaredAnnotations
}

private fun Annotation.getAlias(cls: Class<out Annotation>) = sequence {
	for(method in annotationClass.java.declaredMethods) {
		if(method.isAnnotationPresent(AliasFor::class.java)) {
			val alias = method.getDeclaredAnnotation(AliasFor::class.java)
			val type =
				if(alias.annotation == AliasFor::class.java.getMethod("annotation").defaultValue)
					method.declaringClass
				else
					alias.annotation.java
			if(type == cls)
				yield(method)
		}
	}
}
