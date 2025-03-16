package com.greentree.commons.serialization.format

import com.greentree.commons.serialization.serializator.DeserializationStrategy
import com.greentree.commons.serialization.serializator.SerializationStrategy
import com.greentree.commons.serialization.serializator.deserialize
import com.greentree.commons.serialization.serializator.manager.SerializatorManager
import com.greentree.commons.serialization.serializator.manager.serializator
import com.greentree.commons.serialization.serializator.serialize
import kotlin.reflect.KClass

object XML {

	fun decoder(xml: XmlNode): Decoder = XMLDecoder(xml)
	fun encoder(onResult: (XmlNode) -> Unit): Encoder = XMLEncoder(onResult)

	inline fun <reified T : Any> SerializatorManager.encodeToString(value: T) =
		encodeToString(serializator<T>(), value)

	inline fun <reified T : Any> SerializatorManager.decodeFromString(value: XmlNode) =
		decodeFromString(serializator<T>(), value)

	fun <T : Any> encodeToString(serializer: SerializationStrategy<T>, value: T): XmlNode {
		lateinit var result: XmlNode
		encoder {
			result = it
		}.use { encoder ->
			serializer.serialize(encoder, value)
		}
		return result
	}

	fun <T : Any> decodeFromString(serializer: DeserializationStrategy<T>, value: XmlNode): T {
		return decoder(value).use { decoder ->
			serializer.deserialize(decoder)
		}
	}

	fun <T : Any> SerializatorManager.encodeToString(cls: KClass<in T>, value: T) =
		encodeToString(serializator(cls), value)

	fun <T : Any> SerializatorManager.decodeFromString(cls: KClass<T>, value: XmlNode) =
		decodeFromString(serializator(cls), value)

	fun <T : Any> SerializatorManager.encodeToString(cls: Class<in T>, value: T) =
		encodeToString(serializator(cls), value)

	fun <T : Any> SerializatorManager.decodeFromString(cls: Class<T>, value: XmlNode) =
		decodeFromString(serializator(cls), value)
}

data class XMLEncoder(val onResult: (XmlNode) -> Unit) : Encoder {

	private lateinit var result: XmlNode

	private fun setResult(result: XmlNode) {
		require(!::result.isInitialized) { "already set result current: ${this.result} new: $result" }
		this.result = result
	}

	override fun encodeBoolean(value: Boolean) {
		setResult(BooleanXmlNode(value))
	}

	override fun encodeByte(value: Byte) {
		setResult(ByteXmlNode(value))
	}

	override fun encodeChar(value: Char) {
		setResult(StringXmlNode(value.toString()))
	}

	override fun encodeShort(value: Short) {
		setResult(ShortXmlNode(value))
	}

	override fun encodeInt(value: Int) {
		setResult(IntXmlNode(value))
	}

	override fun encodeLong(value: Long) {
		setResult(LongXmlNode(value))
	}

	override fun encodeFloat(value: Float) {
		setResult(FloatXmlNode(value))
	}

	override fun encodeDouble(value: Double) {
		setResult(DoubleXmlNode(value))
	}

	override fun encodeString(value: String) {
		setResult(StringXmlNode(value))
	}

	override fun beginStructure(): Structure<Encoder> {
		val result = MapTagXmlNode("struct")

		return object : Structure<Encoder> {
			override fun field(name: String): Encoder {
				val res = XMLEncoder {
					when(it) {
						is XmlAttribute -> result.attributes.put(name, it)
						else -> {
							if(it is MutableTagXmlNode) {
								it.name = name
								result.children.add(it)
							} else {
								TODO("$it")
							}
						}
					}
				}
				return res
			}

			override fun field(index: Int): Encoder {
				TODO("Not yet implemented")
			}

			override fun close() {
//				if(result.children.isEmpty() && result.attributes.size == 1) {
//					val (name, value) = result.attributes.entries.single()
//					return setResult(MapTagXmlNode(name, children = mutableListOf(value)))
//				}
//				if(result.children.size == 1 && result.attributes.isEmpty()) {
//					val child = result.children.single()
//					return setResult(child)
//				}
				setResult(result)
			}
		}
	}

	override fun beginCollection(): Structure<Encoder> {
		val result = MapTagXmlNode("collection")

		return object : Structure<Encoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int): XMLEncoder {
				val res = XMLEncoder {
					val element = MapTagXmlNode("element")
					element.children.add(it)
					result.children.add(element)
				}
				return res
			}

			override fun close() {
				setResult(result)
			}
		}
	}

	override fun encodeIntArray(value: IntArray) {
		val result = MapTagXmlNode("array")
		for(i in value) {
			val element = MapTagXmlNode("element")
			element.children.add(IntXmlNode(i))
			result.children.add(element)
		}
		setResult(result)
	}

	override fun close() {
		onResult(result)
	}
}

data class XMLDecoder(private val element: XmlNode) : Decoder {

	override fun decodeBoolean() = (element as BooleanXmlNode).value

	override fun decodeByte() = (element as ByteXmlNode).value

	override fun decodeChar() = (element as CharXmlNode).value

	override fun decodeShort() = (element as ShortXmlNode).value

	override fun decodeInt() = (element as IntXmlNode).value

	override fun decodeLong() = (element as LongXmlNode).value

	override fun decodeFloat() = (element as FloatXmlNode).value

	override fun decodeDouble() = (element as DoubleXmlNode).value

	override fun decodeString() = (element as StringXmlNode).value

	override fun beginStructure(): Structure<Decoder> {
		val element = element as TagXmlNode

		return object : Structure<Decoder> {
			override fun field(name: String) = XMLDecoder(
				element.getAttributeOrNull(name) ?: element.getChildOrNull(name)
				?: throw IllegalArgumentException("not found attribute or child '$name' in ${element.toXmlString()}")
			)

			override fun field(index: Int): Decoder {
				TODO("Not yet implemented")
			}
		}
	}

	override fun beginCollection(): Structure<Decoder> {
		val element = element as TagXmlNode

		return object : Structure<Decoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int) = XMLDecoder((element.children.get(index) as TagXmlNode).children.single())
		}
	}

	override fun decodeIntArray(): IntArray {
		val result = element as TagXmlNode
		return IntArray(result.children.size) {
			val element = result.children[it] as TagXmlNode
			(element.children[0] as IntXmlNode).value
		}
	}

	override fun close() {
	}
}