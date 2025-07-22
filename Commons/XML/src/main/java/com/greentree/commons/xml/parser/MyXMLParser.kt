package com.greentree.commons.xml.parser

import com.greentree.commons.xml.XMLElement
import java.io.InputStream
import java.util.*

object MyXMLParser : XMLParser {

	override fun parse(text: String): XMLElement {
		val text = replaceReference(text)
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
					stack.peek().children.add(BuilderXMLElement(name).build())
				} else {
					if(le != -1) stack.peek().context.append(text.subSequence(le + 1, s).trim())
					if(text[s + 1] == '/') {
						val str1 = text.substring(s + 2, e)
						val my = stack.pop()
						require(str1 == my.name) { "not equals open(" + my.name + ") and close(" + str1 + ")" }
						stack.peek().children.add(my.build())
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
		for(v in stack.peek().children) return v
		throw IllegalArgumentException(text)
	}

	fun replaceReference(text: String): String {
		val builder = StringBuilder()
		var index = 0
		while(index < text.length) {
			val begin = text.indexOf("&", index)
			if(begin == -1) {
				builder.append(text.substring(index, text.length))
				break
			}
			val end = text.indexOf(";", begin)
			if(end == -1) {
				builder.append(text.substring(index, text.length))
				break
			}
			builder.append(text.substring(index, begin))
			val str = text.substring(begin + 1, end)
			val code = when {
				str.startsWith("#x") -> str.substring(2).toInt(16)
				str.startsWith("#") -> str.substring(1).toInt(10)
				else -> getByName(str).code
			}
			builder.append(code.toChar())

			index = end + 1
		}
		return builder.toString().replace("\r", "").trim()
	}

	fun getByName(str: String) = when(str) {
		"quot" -> '\"'
		"lt" -> '<'
		"gt" -> '>'
		else -> TODO(str)
	}

	private fun getText(text: CharSequence): Pair<String, Map<String, String>> {
		val i = text.indexOf(' ')
		return if(i != -1) {
			val name = text.substring(0, i)
			val attributes = text.subSequence(i, text.length)
			Pair(name, getAttributes(attributes))
		} else Pair(text.toString(), HashMap())
	}

	private fun getAttributes(attributes: CharSequence): Map<String, String> {
		var itemsList = attributes.trim().split("\"").map {
			if(it.trimEnd().endsWith("="))
				it.trim()
			else
				it
		}.dropLast(1)
		val items = itemsList.iterator()
		val map: MutableMap<String, String> = HashMap()
		while(items.hasNext()) {
			val a = items.next()
			if(a.endsWith("=")) {
				val b = items.next()
				map[a.substring(0, a.length - 1)] = b
			}
		}
		return map
	}

	private class BuilderXMLElement(
		val name: String,
		val attributes: Map<String, String>,
		val children: MutableList<XMLElement> = ArrayList(),
		val context: StringBuilder = StringBuilder(),
	) {

		constructor() : this("", HashMap())

		constructor(name: String) : this(getText(name))

		constructor(text: Pair<String, Map<String, String>>) : this(text.first, text.second)

		fun build(): XMLElement {
			return XMLElement(children, attributes, name, context.toString())
		}
	}

	override fun parse(inputStream: InputStream): XMLElement = parse(inputStream.buffered().reader().readText())
}

