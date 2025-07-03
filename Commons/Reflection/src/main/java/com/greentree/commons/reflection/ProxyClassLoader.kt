package com.greentree.commons.reflection

import com.greentree.commons.graph.tree
import java.lang.classfile.ClassBuilder
import java.lang.classfile.ClassFile
import java.lang.classfile.ClassFile.*
import java.lang.classfile.constantpool.ConstantPoolBuilder
import java.lang.constant.ClassDesc
import java.lang.constant.ConstantDescs.*
import java.lang.constant.MethodTypeDesc
import java.lang.reflect.AccessFlag
import java.lang.reflect.Method
import java.lang.reflect.Modifier

class ProxyClassLoader(
	parent: ClassLoader = Thread.currentThread().contextClassLoader
) : ClassLoader(parent) {

	private val proxyClasses = mutableMapOf<Set<Class<*>>, Class<*>>()
	private var index = 0

	fun <T : Any> newProxyInstance(
		baseClass: Class<T>,
		interfaces: Array<Class<*>>,
		invocationHandler: MyInvocationHandler
	): T {
		val proxyClass = proxyClasses.getOrPut(setOf(baseClass, *interfaces)) {
			TODO()
		} as Class<T>
		return newInstance(proxyClass, invocationHandler)
	}

	fun newProxyInstance(
		interfaces: Array<Class<*>>,
		invocationHandler: MyInvocationHandler
	): Any {
		val proxyClass = proxyClasses.getOrPut(setOf(*interfaces)) {
			val className = "Proxy${index++}"
			val invocationHandlerClassDesc = ClassDesc.of(MyInvocationHandler::class.java.name)
			val invocationHandlerMethodDesc = MyInvocationHandler::class.java.getMethod(
				"invoke",
				Object::class.java,
				String::class.java,
//				Object::class.java.arrayType()
			).toMethodTypeDesc()
			val invocationHandlerFieldName = "invocationHandler"
			val methods = interfaces.asSequence().flatMap { tree(it) { it.interfaces } }
				.flatMap { it.methods.filterNot { Modifier.isStatic(it.modifiers) } }.toList().distinct()
			val classDesc = ClassDesc.of(className)
			val classClassDesc = ClassDesc.of(Class::class.java.name)
			val constantPool = ConstantPoolBuilder.of()
			val bytes = ClassFile.of()
				.build(constantPool.classEntry(classDesc), constantPool, { cb: ClassBuilder ->
					cb
						.withField(invocationHandlerFieldName, invocationHandlerClassDesc) { fb ->
							fb.withFlags(AccessFlag.FINAL, AccessFlag.PRIVATE)
						}
						.withInterfaces(interfaces.map {
							constantPool.classEntry(ClassDesc.of(it.name))
						})
						.withMethodBody(
							INIT_NAME,
							MethodTypeDesc.of(CD_void, invocationHandlerClassDesc),
							ACC_PUBLIC
						) { cb ->
							cb
								.aload(0)
								.invokespecial(CD_Object, INIT_NAME, MethodTypeDesc.of(CD_void))
								.aload(0)
								.aload(1)
								.putfield(classDesc, invocationHandlerFieldName, invocationHandlerClassDesc)
								.return_()
						}
					for(method in methods) {
						cb.withMethodBody(
							method.name,
							method.toMethodTypeDesc(),
							ACC_PUBLIC
						) { cb ->
							cb
								.aload(0)
								.getfield(classDesc, invocationHandlerFieldName, invocationHandlerClassDesc)
								.aload(0)
								.ldc(constantPool.stringEntry(method.name))
//								.ldc(constantPool.intEntry(method.parameterCount))
//								.anewarray(CD_Object)
								.invokeinterface(invocationHandlerClassDesc, "invoke", invocationHandlerMethodDesc)
								.return_()
						}
					}
				})
			defineClass(className, bytes, 0, bytes.size)
		}
		return newInstance(proxyClass, invocationHandler)
	}

	fun <T : Any> newProxyInstance(
		baseClass: Class<T>,
		invocationHandler: MyInvocationHandler
	): T {
		return if(baseClass.isInterface)
			newProxyInstance(arrayOf(baseClass), invocationHandler) as T
		else
			newProxyInstance(baseClass, arrayOf(), invocationHandler)
	}

	private fun <T : Any> newInstance(cls: Class<T>, handler: MyInvocationHandler): T {
		val constructor = cls.getConstructor(MyInvocationHandler::class.java)
		return constructor.newInstance(handler)
	}
}

private fun Class<*>.toClassDesc(): ClassDesc =
	when {
		this == Void.TYPE -> CD_void
		isArray -> componentType.toClassDesc().arrayType()
		else -> ClassDesc.of(name)
	}

private fun Method.toMethodTypeDesc() =
	MethodTypeDesc.of(returnType.toClassDesc(), parameterTypes.map { it.toClassDesc() })
