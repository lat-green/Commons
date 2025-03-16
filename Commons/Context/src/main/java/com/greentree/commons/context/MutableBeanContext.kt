package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider
import com.greentree.commons.context.provider.InstanceBeanProvider
import com.greentree.commons.context.provider.PrototypeBeanProvider
import com.greentree.commons.context.provider.SingletonBeanProvider
import com.greentree.commons.context.provider.SingletonClassBeanProvider
import com.greentree.commons.context.provider.TransientBeanProvider
import com.greentree.commons.injector.MethodCaller
import kotlin.reflect.KClass

interface MutableBeanContext : BeanContext {

	fun register(name: String, provider: BeanProvider<*>): MutableBeanContext

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

	companion object
}

fun MutableBeanContext.registerInstance(instance: Any) =
	registerInstance(defaultName(instance), instance)

fun <T : Any> MutableBeanContext.registerTransient(factory: BeanRegistration<T>) =
	registerTransient(defaultName(factory), factory)

fun <T : Any> MutableBeanContext.registerSingleton(factory: BeanRegistration<T>) =
	registerSingleton(defaultName(factory), factory)

fun <T : Any> MutableBeanContext.registerPrototype(factory: BeanRegistration<T>) =
	registerPrototype(defaultName(factory), factory)

fun <T : Any> MethodCaller.newInstance(type: KClass<out T>): T = newInstance(type.java)

fun MutableBeanContext.registerSingleton(type: Class<*>) = registerSingleton(defaultName(type), type)

fun MutableBeanContext.registerSingleton(type: KClass<*>) = registerSingleton(type.java)
fun MutableBeanContext.registerSingleton(name: String, type: KClass<*>) = registerSingleton(name, type.java)