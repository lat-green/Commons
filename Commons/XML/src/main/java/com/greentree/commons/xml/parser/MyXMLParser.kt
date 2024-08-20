package com.greentree.commons.xml.parser

import com.greentree.commons.xml.XMLElement
import java.io.InputStream
import java.util.*

object MyXMLParser : XMLParser {

	override fun parse(text: String): XMLElement {
		val stack = Stack<BuilderXMLElement>()
		stack.push(BuilderXMLElement())
		var le = -1
		var s = text.indexOf('<')
		while(s != -1) {
			val e: Int
			if(text[s + 1] == '!') e = text.indexOf("-->", s) + 2
			else {
				e = text.indexOf('>', s)
				if(text[s + 1] != '?' || text[e - 1] != '?') if(text[e - 1] == '/') {
					require(text[s + 1] != '/') { text.substring(s + 1, e) }
					val name = text.substring(s + 1, e - 1)
					stack.peek().childrens.add(BuilderXMLElement(name).build())
				} else {
					if(le != -1) stack.peek().context.append(text.substring(le + 1, s).trim())
					if(text[s + 1] == '/') {
						val str1 = text.substring(s + 2, e)
						val my = stack.pop()
						require(str1 == my.name) { "not equals open(" + my.name + ") and close(" + str1 + ")" }
						stack.peek().childrens.add(my.build())
					} else {
						val str1 = text.substring(s + 1, e)
						stack.push(BuilderXMLElement(str1))
					}
				}
			}
			s = text.indexOf('<', e)
			le = e
		}
		require(stack.size >= 1) { "empty file" }
		require(stack.size <= 1) { "more one xml element is root" }
		for(v in stack.peek().childrens) return v
		throw IllegalArgumentException(text)
	}

	private fun getText(text: String): Pair<String, Map<String, String>> {
		val i = text.indexOf(' ')
		return if(i != -1) {
			val name = text.substring(0, i)
			val attributes = text.substring(i)
			Pair(name, getAttributes(attributes))
		} else Pair(text, HashMap())
	}

	private fun getAttributes(attributes: String): Map<String, String> {
		val map: MutableMap<String, String> = HashMap()
		var space = 0
		while(space != -1) {
			val s = attributes.indexOf('\"', space + 1)
			val e = attributes.indexOf('\"', s + 1)
			val name = attributes.substring(space, attributes.indexOf('=', space + 1))
			val value = attributes.substring(s + 1, e)
			map[name.trim()] = value
			space = attributes.indexOf(' ', e + 1)
		}
		return map
	}

	private class BuilderXMLElement(
		val name: String?,
		val attributes: Map<String, String>,
		val childrens: MutableCollection<XMLElement> = ArrayList(),
		val context: StringBuilder = StringBuilder(),
	) {

		constructor() : this("", HashMap())

		constructor(name: String) : this(getText(name))

		constructor(text: Pair<String, Map<String, String>>) : this(text.first, text.second)

		fun build(): XMLElement {
			return XMLElement(childrens, attributes, name, context.toString())
		}
	}

	override fun parse(inputStream: InputStream): XMLElement = parse(inputStream.buffered().reader().readText())
}

