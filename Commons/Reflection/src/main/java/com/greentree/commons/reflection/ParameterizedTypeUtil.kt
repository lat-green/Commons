package com.greentree.commons.reflection

import com.greentree.commons.util.mapToArray
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass

object ParameterizedTypeUtil {

	fun getRawType(type: Type): Class<*> = when(type) {
		is Class<*> -> type
		is ParameterizedType -> getRawType(type.rawType)
		else -> TODO("getActualTypeArguments(${type::class})")
	}

	fun getActualTypeArguments(type: Type): Array<out Type> = when(type) {
		is Class<*> -> type.typeParameters
		is ParameterizedType -> type.actualTypeArguments
		else -> TODO("getActualTypeArguments(${type::class})")
	}

	fun getTypeArguments(type: ParameterizedType): Map<String, Type> {
		val names = (type.rawType as Class<*>).typeParameters.map { it.name }
		return names.zip(type.actualTypeArguments).associate { it }
	}

	fun getSuperType(type: Type): Type? {
		return when(type) {
			is Class<*> -> {
				return type.genericSuperclass
			}

			is ParameterizedType -> {
				val superRawType = getSuperType(type.rawType) ?: return null
				val map = getTypeArguments(type)
				return superRawType.inject(map)
			}

			else -> TODO("getSuperClass(${type::class})")
		}
	}

	fun getInterfaces(type: Type): Sequence<Type> {
		return when(type) {
			is Class<*> -> {
				return type.genericInterfaces.asSequence()
			}

			is ParameterizedType -> {
				val rawInterfaces = getInterfaces(type.rawType)
				val arguments = getTypeArguments(type)
				return rawInterfaces.map {
					it.inject(arguments)
				}
			}

			else -> TODO("getInterfaces(${type::class})")
		}
	}

	fun getSuperTypeAndInterfaces(type: Type): Sequence<Type> {
		return sequence {
			getSuperType(type)?.let {
				yield(it)
			}
			yieldAll(getInterfaces(type))
		}
	}

	fun getBaseOrNull(
		type: Type,
		superType: Type,
	): Type? {
		when(superType) {
			is ParameterizedType -> {
				return getBaseOrNull(type, superType.rawType)
			}

			is Class<*> -> when(type) {
				is ParameterizedType -> {
					if(!isExtends(superType, type.rawType))
						return null
					if(superType == type.rawType)
						return type
					return getSuperTypeAndInterfaces(type)
						.mapNotNull { getBaseOrNull(it, superType) }
						.firstOrNull()
				}

				is Class<*> -> {
					if(!isExtends(superType, type))
						return null
					if(superType == type)
						return type
					return getSuperTypeAndInterfaces(type)
						.mapNotNull { getBaseOrNull(it, superType) }
						.firstOrNull()
				}

				is WildcardType -> {
					return type.lowerBounds.mapNotNull {
						getBaseOrNull(it, superType)
					}.firstOrNull()
				}

				is TypeVariable<*> -> {
					return type.bounds.mapNotNull {
						getBaseOrNull(it, superType)
					}.firstOrNull()
				}

				is GenericArrayType -> {
					if(superType.isArray)
						TODO("getBaseOrNull(${type::class}, ${superType::class})")
					return null
				}
			}
		}
		TODO("getBaseOrNull(${type::class}, ${superType::class})")
	}

	@JvmStatic
	fun getBaseOrNull(superType: KClass<*>, type: Type) = getBaseOrNull(superType.java, type)

	@JvmStatic
	fun getBaseOrNull(superType: Type, type: KClass<*>) = getBaseOrNull(superType, type.java)

	@JvmStatic
	fun getBaseOrNull(superType: KClass<*>, type: KClass<*>) = getBaseOrNull(superType.java, type.java)

	@JvmStatic
	fun isExtends(superType: Type, type: Type): Boolean {
		if(superType == type)
			return true
		when(superType) {
			is Class<*> -> when(type) {
				is Class<*> -> {
					return superType.isAssignableFrom(type)
				}

				is java.lang.reflect.TypeVariable<*> -> {
					return type.bounds.any {
						isExtends(superType, it)
					}
				}
			}

			is java.lang.reflect.WildcardType -> {
				return superType.lowerBounds.all {
					isExtends(type, it)
				} && superType.upperBounds.all {
					isExtends(it, type)
				}
			}

			is java.lang.reflect.TypeVariable<*> -> {
				return superType.bounds.all {
					isExtends(it, type)
				}
			}
		}
		val base = getBaseOrNull(type, superType) ?: return false
		return getActualTypeArguments(superType).zip(getActualTypeArguments(base)).all {
			isExtends(it.first, it.second)
		}
	}

	@JvmStatic
	fun isExtends(superType: KClass<*>, type: Type) = isExtends(superType.java, type)

	@JvmStatic
	fun isExtends(superType: Type, type: KClass<*>) = isExtends(superType, type.java)

	@JvmStatic
	fun isExtends(superType: KClass<*>, type: KClass<*>) = isExtends(superType.java, type.java)

	@JvmStatic
	fun getArguments(type: Type, superType: Type) =
		getActualTypeArguments(getBaseOrNull(type, superType)!!).map { getRawType(it) }.toTypedArray()
}

fun Type.inject(map: Map<String, Type>): Type = when(this) {
	is java.lang.reflect.TypeVariable<*> -> {
		map[this.name] ?: this
	}

	is WildcardType -> {
		val original = this
		object : WildcardType {
			override fun getUpperBounds(): Array<out Type>? {
				return original.upperBounds.mapToArray { it.inject(map) }
			}

			override fun getLowerBounds(): Array<out Type>? {
				return original.lowerBounds.mapToArray { it.inject(map) }
			}
		}
	}

	is ParameterizedType -> {
		ParameterizedTypeImpl(this.rawType as Class<*>, actualTypeArguments.map { it.inject(map) }.toTypedArray())
	}

	is Class<*> -> {
		if(isArray) {
			val compType = componentType.inject(map)
			if(compType is Class<*>)
				compType.arrayType()
			else
				object : GenericArrayType {
					override fun getGenericComponentType() = compType
				}
		} else {
			val parameters = typeParameters.map { it.inject(map) }
			if(parameters.all { it is java.lang.reflect.TypeVariable<*> })
				this
			else
				ParameterizedTypeImpl(this, typeParameters.map { it.inject(map) }.toTypedArray())
		}
	}

	else -> TODO("${this::class}.inject(${map})")
}