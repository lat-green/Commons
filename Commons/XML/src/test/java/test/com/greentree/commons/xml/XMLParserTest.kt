package test.com.greentree.commons.xml

import com.greentree.commons.xml.parser.XMLParser
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
}