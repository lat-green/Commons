package com.greentree.commons.xml.parser

import com.greentree.commons.util.iterator.IteratorUtil.*
import com.greentree.commons.util.iterator.toListIterator
import com.greentree.commons.xml.XMLElement
import com.greentree.commons.xml.parser.token.Attribute
import com.greentree.commons.xml.parser.token.BeginClose
import com.greentree.commons.xml.parser.token.BeginOpen
import com.greentree.commons.xml.parser.token.End
import com.greentree.commons.xml.parser.token.Space
import com.greentree.commons.xml.parser.token.Text
import com.greentree.commons.xml.parser.token.XMLScanner
import com.greentree.commons.xml.parser.token.XMLTocen
import java.io.InputStream

object LXMLParser : XMLParser {

	override fun parse(input: InputStream): XMLElement {
		val str: String
		input.use {
			str = String(input.readAllBytes())
		}
		return parse(str)
	}

	override fun parse(text: String): XMLElement {
//		run {
//			val iterator = filter(XMLScanner(text)) { it !is Space }.toListIterator()
//			println(iterator.asSequence().toList())
//		}
		val iterator = filter(XMLScanner(text)) { it !is Space }.toListIterator()
		return visitTag(iterator)
	}

	private fun visitTag(iterator: ListIterator<XMLTocen>): XMLElement {
		val begin = iterator.next() as BeginOpen
		val name = begin.name
		val attributes = mutableMapOf<String, String>()
		val children = mutableListOf<XMLElement>()
		val content = StringBuilder()

		visitTagTail(iterator) { (name, value) ->
			attributes[name] = value
		}

		while(iterator.hasNext()) {
			val a = iterator.next()
			if(a is End)
				return XMLElement(children, attributes, name, content.toString())

			if(a is BeginOpen) {
				iterator.previous()
				children.add(visitTag(iterator))
				continue
			}

			if(a is Text) {
				content.append(a.text)
				continue
			}

			TODO("")
		}

		TODO("$iterator")
	}

	private fun visitTagTail(
		iterator: ListIterator<XMLTocen>,
		function: (Attribute) -> Unit
	) {
		while(iterator.hasNext()) {
			when(val currentTocen = iterator.next()) {
				is BeginClose -> return
				is Attribute -> function(currentTocen)
				else -> TODO("$currentTocen")
			}
		}
	}
}
