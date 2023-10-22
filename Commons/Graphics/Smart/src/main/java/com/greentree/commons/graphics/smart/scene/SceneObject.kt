package com.greentree.commons.graphics.smart.scene

import kotlin.reflect.KClass

interface SceneObject {

	val options: Params

	interface Params {

		operator fun get(name: String): Param

		interface Param {

			fun <T : Any> set(cls: Class<T>, value: T)
			operator fun <T : Any> get(cls: Class<T>): T = getOptional(cls)!!
			fun <T : Any> getOptional(cls: Class<T>): T?
		}
	}
}

operator fun <T : Any> SceneObject.Params.Param.get(cls: KClass<T>) = get(cls.java)
inline fun <reified T : Any> SceneObject.Params.Param.get() = get(T::class.java)

inline fun <reified T : Any> SceneObject.Params.Param.set(value: T) = set(T::class.java, value)

fun <T : Any> SceneObject.Params.Param.getOptional(cls: KClass<T>) = getOptional(cls.java)
inline fun <reified T : Any> SceneObject.Params.Param.getOptional() = getOptional(T::class.java)

inline fun <reified T : Any> SceneObject.Params.Param.get(defaultValue: () -> T): T {
	val opt = getOptional<T>()
	if(opt != null)
		return opt
	val def = defaultValue()
	set(def)
	return def
}