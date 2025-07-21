package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.util.iterator.isNotEmpty
import kotlin.reflect.KClass

interface BeanContext {

	fun containsBean(name: String): Boolean = resolveProviderOrNull<Any>(name) != null
	fun containsBean(name: String, type: Class<*>): Boolean = resolveProviderOrNull(name, type) != null
	fun containsBean(type: Class<*>): Boolean = resolveAllProviders(type).isNotEmpty()

	fun <T> resolveProviderOrNull(name: String): BeanProvider<T>?

	fun <T> resolveProviderOrNull(name: String, type: Class<T>): BeanProvider<T>? {
		val provider = resolveProviderOrNull<T>(name) ?: return null
		if(!TypeUtil.isExtends(type, provider.type))
			return null
		return provider
	}

	fun <T> resolveProviderOrNull(type: Class<T>): BeanProvider<T>? {
		return resolveAllProviders(type).one {
			"more one for ('$type') $it"
		}
	}

	fun <T> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>>
	fun <T> resolveAllBeans(type: Class<T>): Sequence<T> = resolveAllProviders(type).map { it.get(this) }

	companion object : () -> MutableBeanContext {

		override operator fun invoke() = BeanContextImpl()
	}
}

fun <T> BeanContext.resolveBeanOrNull(name: String): T? =
	resolveProviderOrNull<T>(name)?.get(this)

fun <T> BeanContext.resolveBeanOrNull(name: String, type: Class<T>): T? = resolveProviderOrNull(name, type)?.get(this)

fun <T> BeanContext.resolveBeanOrNull(type: Class<T>): T? = resolveProviderOrNull(type)?.get(this)

//fun <T> BeanContext.resolveBean(name: String): T =
//	resolveBeanOrNull(name) ?: throw NullPointerException("bean '$name' not found")
fun <T> BeanContext.resolveBean(name: String, type: Class<T>): T =
	resolveBeanOrNull(name, type) ?: throw NullPointerException("bean '$name' $type not found")

fun <T> BeanContext.resolveBean(type: Class<T>): T =
	resolveBeanOrNull(type) ?: throw NullPointerException("bean $type not found")

inline fun <reified T> BeanContext.resolveBean(name: String) = resolveBean(name, T::class.java)
inline fun <reified T> BeanContext.resolveBean() = resolveBean(T::class.java)
inline fun <reified T> BeanContext.resolveAllBeans() = resolveAllBeans(T::class.java)

fun <T : Any> BeanContext.resolveBean(name: String, type: KClass<T>) = resolveBean(name, type.java)
fun <T : Any> BeanContext.resolveBean(type: KClass<T>) = resolveBean(type.java)
fun <T : Any> BeanContext.resolveAllBeans(type: KClass<T>) = resolveAllBeans(type.java)

inline fun <T> Sequence<T>.one(errorMessage: (String) -> String): T? {
	val iter = iterator()
	if(!iter.hasNext())
		return null
	val first = iter.next()
	if(iter.hasNext())
		throw NullPointerException(errorMessage(toList().toString()))
	return first
}