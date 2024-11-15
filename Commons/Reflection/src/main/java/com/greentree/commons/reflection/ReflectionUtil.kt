package com.greentree.commons.reflection

import kotlin.reflect.KClass

@Throws(ClassNotFoundException::class)
fun <T> findClassInAllPackages(baseClass: Class<in T>, className: String): Class<T> =
	ClassUtil.loadClassInAllPackages(baseClass, className) as Class<T>

fun isExtends(superClass: Class<*>, cls: Class<*>): Boolean {
	if(superClass == cls)
		return true
	return superClass.isAssignableFrom(cls)
}

fun isExtends(superClass: KClass<*>, cls: Class<*>): Boolean = isExtends(superClass, cls)
fun isExtends(superClass: Class<*>, cls: KClass<*>): Boolean = isExtends(superClass, cls.java)
fun isExtends(superClass: KClass<*>, cls: KClass<*>): Boolean = isExtends(superClass.java, cls.java)