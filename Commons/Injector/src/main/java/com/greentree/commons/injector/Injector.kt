package com.greentree.commons.injector

import com.greentree.commons.injector.MethodCaller.*
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Parameter

interface Injector {

	fun <T> call(constructor: Constructor<T>): T {
		require(constructor.trySetAccessible()) { "$constructor not accessible" }
		return constructor.newInstance(*Array(constructor.parameterCount) { index ->
			resolve(constructor.parameters[index])
		})
	}

	fun callStatic(
		method: Method,
	): Any {
		require(method.trySetAccessible()) { "$method not accessible" }
		val result = method.invoke(null, *Array(method.parameterCount) { index ->
			resolve(method.parameters[index])
		})
		if(result == null && method.returnType == Void.TYPE)
			return Unit
		return result
	}

	fun call(
		method: Method,
		thisRef: Any,
	): Any {
		require(method.trySetAccessible()) { "$method not accessible" }
		val result = method.invoke(thisRef, *Array(method.parameterCount) { index ->
			resolve(method.parameters[index])
		})
		if(result == null && method.returnType == Void.TYPE)
			return Unit
		return result
	}

	fun setField(
		field: Field,
		thisRef: Any,
	) {
		require(field.trySetAccessible()) { "$field not accessible" }
		val value = resolve(field)
		field.set(thisRef, value)
	}

	fun <T> newInstance(type: Class<T>): T = ((type as Class<Any>).kotlin.objectInstance as T) ?: run {
		val constructors = type.constructors.filter { constructor ->
			isSupports(constructor)
		}
		require(constructors.isNotEmpty()) {
			"$type not support constructors parameters ${
				type.constructors.flatMap { const ->
					const.parameters.filter {
						!isSupports(it)
					}
				}
			}"
		}
		val constructor = constructors.maxBy { it.parameterCount } as Constructor<out T>
		return call(constructor)
	}

	fun isSupports(dependency: Dependency): Boolean

	fun resolve(dependency: Dependency): Any?

	fun builder(): Builder

	interface Builder {

		fun add(resolver: DependencyResolver): Builder

		fun build(): Injector
	}
}

fun Injector.isSupports(method: Method) = method.parameters.all { isSupports(it) }
fun Injector.isSupports(constructor: Constructor<*>) = constructor.parameters.all { isSupports(it) }

fun Injector.isSupports(cls: Class<*>) = isSupports(Dependency.of(cls))
fun Injector.isSupports(field: Field) = isSupports(Dependency.of(field))
fun Injector.isSupports(parameter: Parameter) = isSupports(Dependency.of(parameter))

fun <T> Injector.resolve(cls: Class<T>): T = resolve(Dependency.of(cls)) as T
fun Injector.resolve(field: Field): Any? = resolve(Dependency.of(field))
fun Injector.resolve(parameter: Parameter): Any? = resolve(Dependency.of(parameter))