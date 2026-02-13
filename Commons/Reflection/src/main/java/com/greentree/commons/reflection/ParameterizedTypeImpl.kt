package com.greentree.commons.reflection

import com.greentree.commons.reflection.info.TypeInfo
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

data class ParameterizedTypeImpl(
	private val rawType0: Type,
	private val actualTypeArguments0: Array<out Type>,
	private val ownerType0: Type? = null,
) : ParameterizedType {

	constructor(
		rawType: Class<*>,
		vararg actualTypeArguments: Type,
		ownerType: Type? = rawType.getDeclaringClass(),
	) : this(rawType, actualTypeArguments, ownerType)

	init {
		val rawType = rawType
		if(rawType is Class<*>) {
			require(!rawType.isArray) {
				"$rawType is Array"
			}
			require(rawType.typeParameters.size == actualTypeArguments.size) {
				"$rawType has ${rawType.typeParameters.size} typeParameters, but actualTypeArguments size is ${actualTypeArguments.size}"
			}
			val primitiveActualTypeArguments = actualTypeArguments.filter { TypeInfo<Any>(it).isPrimitive }
			require(primitiveActualTypeArguments.isEmpty()) {
				"actualTypeArguments has primitive ${primitiveActualTypeArguments}"
			}
		}
	}

	override fun getRawType() = rawType0

	override fun getActualTypeArguments() = actualTypeArguments0

	override fun getOwnerType() = ownerType0

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ParameterizedType

		if(rawType != other.rawType) return false
		if(!actualTypeArguments.contentEquals(other.actualTypeArguments)) return false
		if(ownerType != other.ownerType) return false

		return true
	}

	override fun hashCode(): Int {
		var result = rawType.hashCode()
		result = 31 * result + actualTypeArguments.contentHashCode()
		result = 31 * result + (ownerType?.hashCode() ?: 0)
		return result
	}
}