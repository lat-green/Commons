package com.greentree.commons.serialization.format

sealed interface XmlNode {

	fun toXmlString(): String
}

sealed interface XmlAttribute : XmlNode

data class BooleanXmlNode(val value: Boolean) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class ByteXmlNode(val value: Byte) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class CharXmlNode(val value: Char) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class ShortXmlNode(val value: Short) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class IntXmlNode(val value: Int) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class LongXmlNode(val value: Long) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class FloatXmlNode(val value: Float) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class DoubleXmlNode(val value: Double) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data class StringXmlNode(val value: String) : XmlNode, XmlAttribute {

	override fun toXmlString() = value.toString()
}

data object NullXmlNode : XmlNode, XmlAttribute {

	override fun toXmlString() = "null"
}

sealed interface TagXmlNode : XmlNode {

	val name: String
	val children: List<XmlNode>
	val attributes: Map<String, XmlAttribute>

	fun getChildOrNull(name: String): TagXmlNode? = getChildren(name).singleOrNull()
	fun getChild(name: String): TagXmlNode = getChildOrNull(name)!!
	fun getChildren(name: String): Sequence<TagXmlNode> =
		children.asSequence().filterIsInstance<TagXmlNode>().filter { it.name == name }

	fun getAttributeOrNull(name: String): XmlAttribute? = attributes.get(name)
	fun getAttribute(name: String): XmlAttribute = getAttributeOrNull(name)!!
}

sealed interface MutableTagXmlNode : TagXmlNode {

	override var name: String
	override val children: MutableList<XmlNode>
	override val attributes: MutableMap<String, XmlAttribute>
}

data class MapTagXmlNode(
	override var name: String,
	override val children: MutableList<XmlNode> = mutableListOf(),
	override val attributes: MutableMap<String, XmlAttribute> = mutableMapOf(),
) : MutableTagXmlNode {

	override fun toXmlString(): String {
		val name = name
		val result = StringBuilder()
		fun appendAttributes() {
			if(attributes.isNotEmpty()) {
				result.append(' ')
				val attributes =
					attributes.entries.joinToString(" ") { (key, value) -> "$key=\"${value.toXmlString()}\"" }
				result.append(attributes)
			}
		}

		fun appendChildren() {
			if(children.size == 1) {
				val child = children.single()
				if(child !is TagXmlNode) {
					result.append(child.toXmlString())
					return
				}
			}
			for(child in children) {
				result.append('\n')
				result.append('\t')
				result.append(child.toXmlString().replace("\n", "\n\t"))
			}
			result.append('\n')
		}
		if(children.isNotEmpty()) {
			result.append('<')
			result.append(name)
			appendAttributes()
			result.append('>')
			appendChildren()
			result.append("</")
			result.append(name)
			result.append(">")
		} else {
			result.append("<")
			result.append(name)
			appendAttributes()
			result.append("/>")
		}
		return result.toString()
	}
}