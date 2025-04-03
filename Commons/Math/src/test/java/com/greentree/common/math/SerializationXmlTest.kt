package com.greentree.common.math

import com.greentree.commons.serialization.format.XML
import com.greentree.commons.serialization.serializator.deserialize
import com.greentree.commons.serialization.serializator.manager.serializator
import com.greentree.commons.xml.parser.SAXXMLParser
import org.joml.Vector3f
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class SerializationXmlTest : SerializationTest() {

	@ArgumentsSource(BaseArgumentsProvider::class)
	@ParameterizedTest
	fun <T : Any> serialize(expected: T, maxSize: Int) {
		val serializator = manager.serializator(expected::class.java)
		val xml = XML.encodeToString(serializator, expected)
		val decoder = XML.decoder(xml)
		val actual = serializator.deserialize(decoder)
		assertEquals(expected, actual) { "$expected ${xml.toXmlString()}" }
	}

	@Test
	fun deserializeVector3f() {
		val text = """
			<value>
				<x>1</x>
				<y>2</y>
				<z>3</z>
			</value>
		""".trimIndent()
		val xml = text.byteInputStream().use {
			SAXXMLParser.parse(it)
		}
		val decoder = XML.decoder(xml)
		val serializator = manager.serializator<Vector3f>()
		val actual = serializator.deserialize(decoder)
		assertEquals(Vector3f(1f, 2f, 3f), actual)
	}

	@Test
	fun deserializeVector3fOnlyY() {
		val text = """
			<value>
				<y>2</y>
			</value>
		""".trimIndent()
		val xml = text.byteInputStream().use {
			SAXXMLParser.parse(it)
		}
		val decoder = XML.decoder(xml)
		val serializator = manager.serializator<Vector3f>()
		val actual = serializator.deserialize(decoder)
		assertEquals(Vector3f(0f, 2f, 0f), actual)
	}
}