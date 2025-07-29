package test.com.greentree.commons.xml

import com.greentree.commons.tests.aop.AutowiredProvider
import com.greentree.commons.xml.parser.ANTLR4XMLParser
import com.greentree.commons.xml.parser.MyXMLParser
import com.greentree.commons.xml.parser.SAXXMLParser

class XMLParserTestConfig {

	@AutowiredProvider
	fun newANTLR4XMLParser() = ANTLR4XMLParser

	@AutowiredProvider
	fun newSAXXMLParser() = SAXXMLParser

	@AutowiredProvider
	fun newMyXMLParser() = MyXMLParser
}
