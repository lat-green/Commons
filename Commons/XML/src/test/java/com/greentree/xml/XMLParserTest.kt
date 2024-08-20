package com.greentree.xml

import com.greentree.commons.xml.parser.MyXMLParser
import com.greentree.commons.xml.parser.XMLParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.io.InputStream

abstract class XMLParserTest {

	abstract fun runXMLParser(block: (XMLParser) -> Unit)

	@ParameterizedTest
	@ArgumentsSource(XMLArgument::class)
	fun noException(inputStream: InputStream) {
		runXMLParser { parser ->
			inputStream.use {
				parser.parse(it)
			}
		}
	}

	@ParameterizedTest
	@ArgumentsSource(XMLArgument::class)
	fun equalsMyXMLParser(inputStream: InputStream) {
		val text = inputStream.reader().readText()
		runXMLParser { parser ->
			val xml1 = parser.parse(text)
			val xml2 = MyXMLParser.parse(text)
			assertEquals(xml1, xml2)
		}
	}
}
