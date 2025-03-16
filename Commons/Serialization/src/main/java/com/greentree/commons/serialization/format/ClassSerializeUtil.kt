package com.greentree.commons.serialization.format

import com.greentree.commons.serialization.serializator.type.ClassAsNameSerializator
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.lang.reflect.Array
import kotlin.reflect.KClass

object ClassSerializeUtil {

	private val packageNames = sequence {
		yield("java.lang")
		yield("java.util")
		yield("java.io")
		yield("java.net")
		ClassAsNameSerializator::class.java.module.layer?.let { layer ->
			layer.modules().forEach {
				yieldAll(it.packages)
			}
		}
		var classLoader: ClassLoader? = Thread.currentThread().contextClassLoader
		while(classLoader != null) {
			yieldAll(classLoader.definedPackages.map { it.name })
			classLoader = classLoader.parent
		}
	}.toSet().toList()
	private val finder: ClassNameFinder = CacheNameFinder(ArrayClassNameFinder(FrequentClasses(SimpleName(FullName))))

	fun encodeClass(encoder: Encoder, cls: Class<*>) = encoder.encodeString(finder.encodeClass(cls))

	fun decodeClass(decoder: Decoder) = finder.decodeClass(decoder.decodeString())

	interface ClassNameFinder {

		fun encodeClass(cls: Class<*>): String
		fun decodeClass(shortCode: String): Class<*>
	}

	data class CacheNameFinder(val next: ClassNameFinder) : ClassNameFinder {

		private val encodeClass = mutableMapOf<Class<*>, String>()
		private val decodeClass = mutableMapOf<String, Class<*>>()

		override fun encodeClass(cls: Class<*>) = encodeClass.getOrPut(cls) { next.encodeClass(cls) }
		override fun decodeClass(shortCode: String) = decodeClass.getOrPut(shortCode) { next.decodeClass(shortCode) }
	}

	data class ArrayClassNameFinder(val next: ClassNameFinder) : ClassNameFinder {

		override fun encodeClass(cls: Class<*>): String {
			if(cls.isArray) {
				return "[${finder.encodeClass(cls.componentType)}"
			}
			return next.encodeClass(cls)
		}

		override fun decodeClass(shortCode: String): Class<*> {
			if(shortCode.startsWith('[')) {
				val componentType = finder.decodeClass(shortCode.substring(1))
				return Array.newInstance(componentType, 0)::class.java
			}
			return next.decodeClass(shortCode)
		}
	}

	data object FullName : ClassNameFinder {

		override fun encodeClass(cls: Class<*>): String = cls.name

		override fun decodeClass(shortCode: String): Class<*> = Class.forName(shortCode)
	}

	data class SimpleName(val next: ClassNameFinder) : ClassNameFinder {

		override fun encodeClass(cls: Class<*>): String {
			if(cls.packageName in packageNames) {
				val shortCode = cls.nameWithoutPackage
				val allClasses = packageNames.mapNotNull { runCatching { Class.forName("$it.$shortCode") }.getOrNull() }
				if(allClasses.size == 1)
					return shortCode
			}
			return next.encodeClass(cls)
		}

		override fun decodeClass(shortCode: String): Class<*> {
			if('.' !in shortCode) {
				val allClasses = packageNames.mapNotNull { runCatching { Class.forName("$it.$shortCode") }.getOrNull() }
				return allClasses.single()
			}
			return next.decodeClass(shortCode)
		}
	}

	data class FrequentClasses(val next: ClassNameFinder) : ClassNameFinder {

		private val encodeClassMap = mutableMapOf<Class<*>, String>()
		private val decodeClassMap = mutableMapOf<String, Class<*>>()

		fun add(shortCode: String, cls: Class<*>) {
			encodeClassMap.put(cls, shortCode)
			decodeClassMap.put(shortCode, cls)
		}

		private val defaultShortCode =
			(Char.MIN_VALUE .. Char.MAX_VALUE)
				.asSequence()
				.map { it.toChar() }
				.filter { !it.isLetter() && it !in listOf('.', '[') }
				.distinct()
				.map { it.toString() }
				.filter {
					ByteArrayOutputStream().use { bout ->
						DataOutputStream(bout).use { out ->
							out.writeUTF(it)
						}
						bout.toByteArray().size == 3
					}
				}
				.iterator()

		private fun add(cls: Class<*>) = add(defaultShortCode.next(), cls)
		private fun add(cls: KClass<*>) = add(cls.java)
		private inline fun <reified T> add() = add(T::class.java)

		init {
			add<String>()

			add(Integer::class)
			add(java.lang.Short::class)
			add(Character::class)
			add(java.lang.Byte::class)

			add(java.lang.Double::class)
			add(java.lang.Float::class)

			add(java.lang.Boolean::class)

			add(Integer.TYPE)
			add(java.lang.Short.TYPE)
			add(Character.TYPE)
			add(java.lang.Byte.TYPE)

			add(java.lang.Double.TYPE)
			add(java.lang.Float.TYPE)

			add(java.lang.Boolean.TYPE)

			add(Any::class)
			add(Unit::class)

			add(ArrayList::class)
			add(HashSet::class)
			add(HashMap::class)
		}

		override fun encodeClass(cls: Class<*>): String {
			return encodeClassMap[cls] ?: next.encodeClass(cls)
		}

		override fun decodeClass(shortCode: String): Class<*> {
			return decodeClassMap[shortCode] ?: next.decodeClass(shortCode)
		}
	}
}

val Class<*>.nameWithoutPackage: String
	get() = if('$' in name)
		if('.' in name)
			name.removePrefix(packageName).substring(1)
		else
			name
	else
		simpleName