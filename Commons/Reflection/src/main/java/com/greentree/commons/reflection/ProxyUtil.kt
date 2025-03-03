package com.greentree.commons.reflection

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object ProxyUtil {

	fun <T> newLazyInstance(initializer: () -> T) = newLazyInstance(returnType(initializer), initializer)

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

	fun <T> newStreamingInstance(initializer: () -> T) = newStreamingInstance(returnType(initializer), initializer)

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

fun <R> returnType(factory: Function0<R>): Class<out R> =
	TypeUtil.getSuperType(
		getTypeInfo(factory::class.java),
		Function0::class.java
	).typeArguments[0].toClass() as Class<out R>

fun <R> returnType(factory: Function1<*, R>): Class<out R> =
	TypeUtil.getSuperType(
		getTypeInfo(factory::class.java),
		Function1::class.java
	).typeArguments[1].toClass() as Class<out R>

fun <R> returnType(factory: Function2<*, *, R>): Class<out R> =
	TypeUtil.getSuperType(
		getTypeInfo(factory::class.java),
		Function2::class.java
	).typeArguments[2].toClass() as Class<out R>

fun <R> returnType(factory: Function3<*, *, *, R>): Class<out R> =
	TypeUtil.getSuperType(
		getTypeInfo(factory::class.java),
		Function3::class.java
	).typeArguments[3].toClass() as Class<out R>