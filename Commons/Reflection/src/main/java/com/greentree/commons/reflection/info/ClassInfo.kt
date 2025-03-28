package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.util.function.AbstractSaveFunction
import java.lang.reflect.Type
import java.util.*

class ClassInfo<C> private constructor(private val cls: Class<C>) : TypeInfo<C> {

	override val typeArguments: Array<TypeInfo<out Any>>
		get() = emptyArray()
	override val interfaces: Array<TypeInfo<in C>>
		get() = cls.genericInterfaces.map { getTypeInfo<Any>(it) as TypeInfo<in C> }.toTypedArray()
	override val boxing: TypeInfo<C>
		get() {
			if(isPrimitive) return INSTANCIES.get(
				ClassUtil.getNotPrimitive(
					cls
				)
			)
			return this
		}
	override val superType: TypeInfo<in C>?
		get() {
			val superClass = cls.genericSuperclass ?: return null
			return getTypeInfo(superClass)
		}
	override val simpleName: String
		get() = cls.simpleName
	override val name: String
		get() = cls.name
	override val isPrimitive: Boolean
		get() = cls.isPrimitive

	override fun toClass(): Class<C> {
		return cls
	}

	override fun isChildFor(parent: TypeInfo<*>): Boolean {
		if(parent is ClassInfo<*>)
			return parent.toClass().isAssignableFrom(cls)
		return parent.isParentFor(this)
	}

	override fun isParentFor(child: TypeInfo<*>): Boolean {
		if(child is ClassInfo<*>)
			return cls.isAssignableFrom(child.toClass())
		return child.isChildFor(this)
	}

	override fun isChildFor(parent: Class<*>): Boolean = parent.isAssignableFrom(cls)
	override fun isParentFor(child: Class<*>): Boolean = cls.isAssignableFrom(child)

	override val typeName: CharSequence
		get() = cls.typeName
	override val type: Type
		get() = cls

	override fun hashCode(): Int {
		return Objects.hash(cls)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj == null || javaClass != obj.javaClass) return false
		val other = obj as ClassInfo<*>
		return cls == other.cls
	}

	override fun toString(): String {
		return cls.toString()
	}

	private class ClassInfoSingletonFactory
		: AbstractSaveFunction<Class<*>, ClassInfo<*>>() {

		fun <C> get(cls: Class<C>): ClassInfo<C> {
			return applyRaw(cls) as ClassInfo<C>
		}

		override fun create(cls: Class<*>): ClassInfo<*> {
			return ClassInfo(cls as Class<Any>)
		}
	}

	companion object {

		private val INSTANCIES = ClassInfoSingletonFactory()

		fun <C> get(cls: Class<C>): ClassInfo<C> {
			return INSTANCIES.get(cls)
		}
	}
}
