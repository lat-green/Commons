package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider
import com.greentree.commons.context.provider.InstanceBeanProvider
import com.greentree.commons.context.provider.PrototypeBeanProvider
import com.greentree.commons.context.provider.SingletonBeanProvider
import com.greentree.commons.context.provider.SingletonClassBeanProvider
import com.greentree.commons.context.provider.TransientBeanProvider
import com.greentree.commons.reflection.info.TypeInfo
import kotlin.reflect.KClass

interface MutableBeanContext : BeanContext {

	fun register(name: String, provider: BeanProvider<*>): MutableBeanContext

	fun registerInstance(name: String, instance: Any) =
		register(name, InstanceBeanProvider(instance))

	fun <T : Any> registerPrototype(name: String, factory: BeanRegistration<T>) =
		register(name, PrototypeBeanProvider(factory))

	fun <T : Any> registerSingleton(name: String, factory: BeanRegistration<T>) =
		register(name, SingletonBeanProvider(factory))

	fun <T : Any> registerTransient(name: String, factory: BeanRegistration<T>) =
		register(name, TransientBeanProvider(factory))

	fun <T : Any> registerSingleton(name: String, type: TypeInfo<T>) =
		register(name, SingletonClassBeanProvider(type))

	companion object
}

fun MutableBeanContext.registerInstance(instance: Any) =
	registerInstance(defaultName(instance::class), instance)

fun <T : Any> MutableBeanContext.registerTransient(factory: BeanRegistration<T>) =
	registerTransient(defaultName(factory), factory)

fun <T : Any> MutableBeanContext.registerSingleton(factory: BeanRegistration<T>) =
	registerSingleton(defaultName(factory), factory)

fun <T : Any> MutableBeanContext.registerPrototype(factory: BeanRegistration<T>) =
	registerPrototype(defaultName(factory), factory)

fun MutableBeanContext.registerSingleton(type: TypeInfo<*>) = registerSingleton(defaultName(type), type)

fun MutableBeanContext.registerSingleton(cls: Class<*>) = registerSingleton(TypeInfo(cls))
fun MutableBeanContext.registerSingleton(name: String, cls: Class<*>) = registerSingleton(name, TypeInfo(cls))

fun MutableBeanContext.registerSingleton(cls: KClass<*>) = registerSingleton(cls.java)
fun MutableBeanContext.registerSingleton(name: String, cls: KClass<*>) = registerSingleton(name, cls.java)