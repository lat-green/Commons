package com.greentree.commons.xml.parser

import com.greentree.commons.xml.XMLElement
import com.greentree.commons.xml.parser.MyXMLParser.replaceReference
import org.w3c.dom.Element
import org.w3c.dom.Text
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

@Deprecated("")
data object SAXXMLParser : XMLParser {

	override fun parse(inputStream: InputStream): XMLElement {
		val builder: DocumentBuilder = factory.newDocumentBuilder()
		builder.setErrorHandler(DefaultHandler())
		val `is` = InputSource(inputStream)
		val doc = builder.parse(`is`)
		return build(doc.getDocumentElement())
	}

	private val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()

	@Throws(IOException::class, ParserConfigurationException::class)
	private fun build(xmlElement: Element): XMLElement {
		val name = xmlElement.getTagName()
		val children = mutableListOf<XMLElement>()
		val list = xmlElement.getChildNodes()
		val builder = StringBuilder()
		for(i in 0 ..< list.getLength()) {
			val item = list.item(i)
			if(item is Element) children.add(build(item))
			if(item is Text) builder.append(item.getNodeValue().trim())
		}
		val attributes = HashMap<String, String>()
		val map = xmlElement.getAttributes()
		for(i in 0 ..< map.getLength()) {
			val a = map.item(i)
			val n = a.getNodeName()
			val v = a.getTextContent()
			attributes.put(n, v)
		}
		val content = replaceReference(builder.toString())
		return XMLElement(children, attributes, name, content)
	}
}
