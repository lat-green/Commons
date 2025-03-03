package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.util.function.AbstractSaveFunction
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.*
import java.util.stream.Collectors

data class ParameterizedTypeInfo<C> private constructor(
	val parameterizedType: ParameterizedType,
) : TypeInfo<C> {

	private val parametersMap: Map<String, Type>
		get() {
			val map = HashMap<String, Type>()
			run {
				val names = toClass().typeParameters
				val types = parameterizedType.actualTypeArguments
				val len = names.size
				assert(len == types.size)
				for(i in 0 until len) map[names[i].name] = types[i]
			}
			return map
		}
	val rawType: TypeInfo<C>
		get() = getTypeInfo(parameterizedType.rawType)

	override fun hashCode(): Int {
		return Objects.hash(parameterizedType)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj == null || javaClass != obj.javaClass) return false
		val other = obj as ParameterizedTypeInfo<*>
		return parameterizedType == other.parameterizedType
	}

	override fun toString(): String {
		return parameterizedType.toString()
	}

	override fun toClass(): Class<C> {
		return parameterizedType.rawType as Class<C>
	}

	override val typeArguments: Array<TypeInfo<out Any>>
		get() {
			val args = parameterizedType.actualTypeArguments
			return Array(args.size) {
				getTypeInfo<Any>(args[it])
			}
		}
	override val interfaces: Array<TypeInfo<in C>>
		get() {
			val clazz = toClass()
			val map = parametersMap
			val `is` = clazz.genericInterfaces
			return Array(`is`.size) {
				getTypeInfo(map(`is`[it], map))
			}
		}
	override val boxing: TypeInfo<C>
		get() = this
	override val superType: TypeInfo<in C>?
		get() {
			val cls = toClass()
			val superClass = cls.genericSuperclass ?: return null
			return getTypeInfo(map(superClass))
		}
	override val simpleName: String
		get() {
			val joiner = StringJoiner(", ", "<", ">")
			for(arg in typeArguments) {
				joiner.add(arg.simpleName)
			}
			return toClass().simpleName + joiner
		}
	override val name: String
		get() = parameterizedType.typeName

	override fun isParentFor(child: TypeInfo<*>): Boolean {
		return dfsTo(child, this)
	}

	override fun isChildFor(parent: TypeInfo<*>): Boolean {
		if(parent is ParameterizedTypeInfo<*>) {
			if(ClassUtil.isExtends(parent.toClass(), toClass()))
				return dfsTo(this, parent)
			return false
		}
		return parent.toClass().isAssignableFrom(toClass())
	}

	override val typeName: CharSequence
		get() = parameterizedType.typeName
	override val type: Type
		get() = parameterizedType

	fun map(type: Type) = map(type, parametersMap)

	private class ParameterizedTypeInfoSingletonFactory :
		AbstractSaveFunction<ParameterizedType, ParameterizedTypeInfo<*>>() {

		fun <C> get(type: ParameterizedType): ParameterizedTypeInfo<C> {
			return applyRaw(type) as ParameterizedTypeInfo<C>
		}

		override fun create(type: ParameterizedType): ParameterizedTypeInfo<*> {
			if(type.actualTypeArguments.any { it is TypeVariable<*> })
				throw IllegalArgumentException("" + type)
			return ParameterizedTypeInfo<Any>(type)
		}

		companion object {

			private const val serialVersionUID = 1L
		}
	}

	companion object {

		private val INSTANCIES = ParameterizedTypeInfoSingletonFactory()

		fun <C> get(type: ParameterizedType): ParameterizedTypeInfo<C> {
			return INSTANCIES.get(type)
		}

		private fun dfsTo(type: ClassInfo<*>, superType: ParameterizedTypeInfo<*>): Boolean {
			if(!ClassUtil.isExtends(superType.toClass(), type.toClass())) return false
			for(s in type.getSuperClassAndInterfaces()) if(dfsTo(s, superType)) return true
			return false
		}

		private fun dfsTo(
			type: ParameterizedTypeInfo<*>,
			superType: ParameterizedTypeInfo<*>,
		): Boolean {
			if(!ClassUtil.isExtends(superType.toClass(), type.toClass())) return false
			if(superType == type) return true
			for(s in type.getSuperClassAndInterfaces()) if(dfsTo(s, superType)) return true
			return false
		}

		private fun dfsTo(type: TypeInfo<*>, superType: ParameterizedTypeInfo<*>): Boolean {
			if(type is ParameterizedTypeInfo<*>) return dfsTo(type, superType)
			if(type is ClassInfo<*>) return dfsTo(
				type, superType
			)
			return false
		}

		private fun map(type: Type, map: Map<String, Type>): Type {
			if(type is ParameterizedType) {
				val types_list = Arrays.asList(*type.actualTypeArguments).stream().map { t: Type? ->
					if(t is TypeVariable<*>) return@map map[t.name]
					if(t is ParameterizedType) return@map map(t, map)
					null
				}.collect(Collectors.toList())
				val types = types_list.toTypedArray()
				val raw: Type = type.rawType
				return GenericType.build(raw as Class<*>, *types)
			}
			return type
		}
	}
}
