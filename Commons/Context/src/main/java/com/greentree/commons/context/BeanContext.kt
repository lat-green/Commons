package com.greentree.commons.context

import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.rex.context.argument.MethodCaller
import com.greentree.commons.context.provider.BeanProvider
import com.greentree.commons.context.provider.InstanceBeanProvider
import com.greentree.commons.context.provider.PrototypeBeanProvider
import com.greentree.commons.context.provider.SingletonBeanProvider
import com.greentree.commons.context.provider.SingletonClassBeanProvider
import com.greentree.commons.context.provider.TransientBeanProvider
import kotlin.reflect.KClass

interface BeanContext {

	fun containsBean(name: String): Boolean = resolveProviderOrNull<Any>(name) != null
	fun containsBean(name: String, type: Class<*>): Boolean = resolveProviderOrNull(name, type) != null
	fun containsBean(type: Class<*>): Boolean = resolveProviderOrNull(type) != null

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

	interface Builder {

		fun register(name: String, provider: BeanProvider<*>): Builder

		fun registerInstance(name: String, instance: Any) =
			register(name, InstanceBeanProvider(instance))

		fun <T> registerPrototype(name: String, factory: BeanRegistration<T>) =
			register(name, PrototypeBeanProvider(factory))

		fun <T> registerSingleton(name: String, factory: BeanRegistration<T>) =
			register(name, SingletonBeanProvider(factory))

		fun <T> registerTransient(name: String, factory: BeanRegistration<T>) =
			register(name, TransientBeanProvider(factory))

		fun <T> registerSingleton(name: String, type: Class<T>) =
			register(name, SingletonClassBeanProvider(type))

		fun build(): BeanContext

		companion object {

			operator fun invoke() = BeanContextImpl.Builder()
		}
	}

	companion object
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

fun BeanContext.Builder.registerInstance(instance: Any) =
	registerInstance(defaultName(instance), instance)

fun <T : Any> BeanContext.Builder.registerTransient(factory: BeanRegistration<T>) =
	registerTransient(defaultName(factory), factory)

fun <T : Any> BeanContext.Builder.registerSingleton(factory: BeanRegistration<T>) =
	registerSingleton(defaultName(factory), factory)

fun <T : Any> BeanContext.Builder.registerPrototype(factory: BeanRegistration<T>) =
	registerPrototype(defaultName(factory), factory)

inline fun <T> Sequence<T>.one(errorMessage: (String) -> String): T? {
	val iter = iterator()
	if(!iter.hasNext())
		return null
	val first = iter.next()
	if(iter.hasNext())
		throw NullPointerException(errorMessage(toList().toString()))
	return first
}

fun <T : Any> MethodCaller.newInstance(type: KClass<out T>): T = newInstance(type.java)

fun BeanContext.Builder.registerSingleton(type: Class<*>) = registerSingleton(defaultName(type), type)

fun BeanContext.Builder.registerSingleton(type: KClass<*>) = registerSingleton(type.java)
fun BeanContext.Builder.registerSingleton(name: String, type: KClass<*>) = registerSingleton(name, type.java)