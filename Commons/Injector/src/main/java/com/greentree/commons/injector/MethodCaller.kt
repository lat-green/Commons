@file:JvmName("MethodCallerImplKt")

package com.greentree.commons.injector

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Parameter
import kotlin.reflect.KClass

interface MethodCaller {

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
		require(!Modifier.isStatic(field.modifiers)) { "$field is static" }
		require(field.trySetAccessible()) { "$field not accessible" }
		val value = resolve(field)
		field.set(thisRef, value)
	}

	fun setStaticField(
		field: Field,
	) {
		require(Modifier.isStatic(field.modifiers)) { "$field is not static" }
		require(field.trySetAccessible()) { "$field not accessible" }
		val value = resolve(field)
		field.set(null, value)
	}

	fun setFieldIfResolve(
		field: Field,
		thisRef: Any,
	) {
		if(isSupports(field)) {
			return setField(field, thisRef)
		}
	}

	fun setStaticFieldIfResolve(
		field: Field,
	) {
		if(isSupports(field)) {
			return setStaticField(field)
		}
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
	fun resolveAll(dependency: Dependency): Sequence<Any>

	fun builder(): Builder

	interface Builder {

		fun add(resolver: DependencyResolver): Builder

		fun build(): MethodCaller
	}
}

fun <T : Any> MethodCaller.newInstance(type: KClass<out T>): T = newInstance(type.java)

fun MethodCaller.isSupports(method: Method) = method.parameters.all { isSupports(it) }
fun MethodCaller.isSupports(constructor: Constructor<*>) = constructor.parameters.all { isSupports(it) }

fun MethodCaller.isSupports(cls: Class<*>) = isSupports(Dependency.of(cls))
fun MethodCaller.isSupports(field: Field) = isSupports(Dependency.of(field))
fun MethodCaller.isSupports(parameter: Parameter) = isSupports(Dependency.of(parameter))

fun <T> MethodCaller.resolve(cls: Class<T>): T = resolve(Dependency.of(cls)) as T
fun MethodCaller.resolve(field: Field): Any? = resolve(Dependency.of(field))
fun MethodCaller.resolve(parameter: Parameter): Any? = resolve(Dependency.of(parameter))

fun <T> MethodCaller.resolveAll(cls: Class<T>) = resolveAll(Dependency.of(cls)) as Sequence<T>
fun MethodCaller.resolveAll(parameter: Field) = resolveAll(Dependency.of(parameter))
fun MethodCaller.resolveAll(parameter: Parameter) = resolveAll(Dependency.of(parameter))