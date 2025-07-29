package test.com.greentree.commons.xml

import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.tests.aop.AutowiredTest
import com.greentree.commons.xml.parser.XMLParser
import java.io.InputStream

@AutowiredConfig(XMLParserTestConfig::class)
class XMLParserTest {

	@AutowiredTest(XMLArgument::class)
	fun noException(parser: XMLParser, inputStream: InputStream) {
		inputStream.use {
			parser.parse(it)
		}
	}
}