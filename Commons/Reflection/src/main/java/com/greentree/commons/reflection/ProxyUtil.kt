package com.greentree.commons.reflection

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object ProxyUtil {

	fun <T : Any> newLazyInstance(initializer: () -> T) =
		newLazyInstance(returnType(initializer), initializer)

	fun <T : Any> newLazyInstance(type: TypeInfo<out T>, initializer: () -> T): T {
		return newLazyInstance(type.toClass(), initializer)
	}

	fun <T> newLazyInstance(cls: Class<out T>, initializer: () -> T): T {
		return Proxy.newProxyInstance(
			Thread.currentThread().contextClassLoader,
			arrayOf(cls),
			newLazyInvocationHandler(initializer)
		) as T
	}

	fun <T> newLazyInstance(vararg interfaces: Class<out T>, initializer: () -> T): T {
		return Proxy.newProxyInstance(
			Thread.currentThread().contextClassLoader,
			interfaces,
			newLazyInvocationHandler(initializer)
		) as T
	}

	fun <T> newLazyInvocationHandler(initializer: () -> T) = object : InvocationHandler {
		private val origin by lazy(initializer)

		override fun invoke(proxy: Any, method: Method, args: Array<out Any?>?): Any {
			when(method.name) {
				"equals" -> {
					return if(args == null)
						method.invoke(origin)
					else {
						val other = args[0]
						if(other == null)
							false
						else {
							if(origin == other)
								true
							else
								method.invoke(other, origin)
						}
					}
				}
			}
			return if(args == null)
				method.invoke(origin)
			else
				method.invoke(origin, *args)
		}
	}

	fun <T : Any> newStreamingInstance(initializer: () -> T) =
		newStreamingInstance(returnType(initializer).toClass(), initializer)

	fun <T> newStreamingInstance(cls: Class<out T>, initializer: () -> T): T {
		return Proxy.newProxyInstance(
			Thread.currentThread().contextClassLoader,
			arrayOf(cls),
			newStreamingInvocationHandler(initializer)
		) as T
	}

	fun <T> newStreamingInstance(vararg interfaces: Class<out T>, initializer: () -> T): T {
		return Proxy.newProxyInstance(
			Thread.currentThread().contextClassLoader,
			interfaces,
			newStreamingInvocationHandler(initializer)
		) as T
	}

	fun <T> newStreamingInvocationHandler(initializer: () -> T) = object : InvocationHandler {
		private val origin
			get() = initializer()

		override fun invoke(proxy: Any, method: Method, args: Array<out Any?>?): Any? {
			return if(args == null)
				method.invoke(origin)
			else
				method.invoke(origin, *args)
		}
	}
}

fun <R : Any> returnType(factory: Function0<R>): TypeInfo<out R> =
	TypeUtil.getSuperType(
		TypeInfo(factory::class.java),
		Function0::class.java
	).typeArguments[0] as TypeInfo<out R>

fun <R : Any> returnType(factory: Function1<*, R>): TypeInfo<out R> =
	TypeUtil.getSuperType(
		TypeInfo(factory::class.java),
		Function1::class.java
	).typeArguments[1] as TypeInfo<out R>

fun <R : Any> returnType(factory: Function2<*, *, R>): TypeInfo<out R> =
	TypeUtil.getSuperType(
		TypeInfo(factory::class.java),
		Function2::class.java
	).typeArguments[2] as TypeInfo<out R>

fun <R : Any> returnType(factory: Function3<*, *, *, R>): TypeInfo<out R> =
	TypeUtil.getSuperType(
		TypeInfo(factory::class.java),
		Function3::class.java
	).typeArguments[3] as TypeInfo<out R>