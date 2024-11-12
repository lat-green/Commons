package com.greentree.commons.xml

import java.io.Serializable
import java.util.*
import java.util.stream.Collectors

data class XMLElement(
	val children: Collection<XMLElement>,
	val attributes: Map<String, String>,
	val name: String,
	val content: String,
) : Serializable, Map<String, String> by attributes {

	fun getAttribute(name: String, def: String): String {
		val value = getAttribute(name)
		if(value == null || value.length == 0) return def
		return value
	}

	fun getAttribute(name: String) = attributes[name]

	val attributeNames: Array<String>
		get() {
			val names = attributes.keys
			return names.toTypedArray<String>()
		}

	fun getChild(name: String): XMLElement? {
		val xmls: Iterable<XMLElement> = getChildren(name)
		for(xml in xmls) return xml
		return null
	}

	fun getChildren(name: String): Collection<XMLElement> {
		return children.stream().filter { xml: XMLElement -> xml.name == name }
			.collect(Collectors.toList())
	}

	override fun hashCode(): Int {
		var result = Objects.hashCode(children)
		result = 31 * result + Objects.hashCode(attributes)
		result = 31 * result + Objects.hashCode(name)
		result = 31 * result + Objects.hashCode(content)
		return result
	}

	override fun equals(o: Any?): Boolean {
		if(this === o) return true
		if(o == null || javaClass != o.javaClass) return false
		val that = o as XMLElement
		return children == that.children && attributes == that.attributes && name == that.name && content.trim { it <= ' ' } == that.content.trim { it <= ' ' }
	}

	override fun toString(): String {
		val res = StringBuilder("<").append(name)
		for(n in attributes.keys) res.append(" ").append(n).append("=\"").append(getAttribute(n)).append("\"")
		if(children.isEmpty()) {
			if(content.isBlank()) res.append("/>")
			else {
				res.append(">")
				if(content.indexOf('\n') != -1) res.append("\n\t").append(content).append("\n")
				else res.append(content)
				res.append("</").append(name).append(">")
			}
		} else {
			res.append(">")
			if(content.indexOf('\n') != -1) res.append("\n\t").append(content).append("\n")
			else res.append(content)
			for(e in children) res.append(
				"""
$e""".replace("\n", "\n\t")
			)
			res.append("\n</").append(name).append(">")
		}
		return res.toString()
	}
}
