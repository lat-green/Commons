package com.greentree.commons.xml.parser.token

import java.util.regex.Pattern

sealed interface XMLTocen {
	sealed interface Type<Token : XMLTocen> {

		fun find(text: CharSequence): Matcher<Token>?
	}
}

data class Matcher<T : XMLTocen>(
	val token: List<T>,
	val end: Int
) {

	constructor(
		token: T,
		end: Int
	) : this(listOf(token), end)
}

val xmlTocenTypes = sequenceOf(
	Space,
	TagXmlTocenType,
	Attribute,
	Text,
)

data object Space : XMLPatternTocen<Space>("[ \\n\\t\\r]+")

data object End : XMLTocen
data object BeginClose : XMLTocen
data class BeginOpen(val name: String) : XMLTocen
data class Attribute(val name: String, val value: String) : XMLTocen {

	constructor(name: CharSequence, value: CharSequence) : this(name.toString(), value.toString())

	companion object : XMLTocen.Type<Attribute> {

		override fun find(text: CharSequence): Matcher<Attribute>? {
			val equalsIndex = text.indexOf('=')
			if(equalsIndex == -1)
				return null
			val beginValue = text.indexOf('\"', equalsIndex + 1)
			if(beginValue == -1)
				return null
			val endValue = text.indexOf('\"', beginValue + 1)
			if(endValue == -1)
				return null
			val name = text.subSequence(0 ..< equalsIndex).trim()
			if(listOf(" ", "<", ">").any { it in name })
				return null
			val value = text.subSequence(beginValue + 1 ..< endValue).trim()
			return Matcher(Attribute(name, value), endValue + 1)
		}
	}
}

data class Text(val text: String) : XMLTocen {

	companion object : XMLPatternTocenType<Text>("[^<>]*\\w[^<>]*") {

		override fun newTocen(text: String) = Text(text)
	}
}

data object TagXmlTocenType : XMLTocen.Type<XMLTocen> {

	override fun find(text: CharSequence): Matcher<XMLTocen>? {
		if(text.startsWith("</")) {
			val end = text.indexOf('>')
			return Matcher(End, end + 1)
		}
		if(text.startsWith("<")) {
			val end = text.indexOfFirst { it in listOf(' ', '>', '/') }
			val name = text.substring(1, end)
			return Matcher(BeginOpen(name), end)
		}
		if(text.startsWith(">")) {
			return Matcher(BeginClose, 1)
		}
		if(text.startsWith("/>")) {
			return Matcher(listOf(BeginClose, End), 2)
		}
		return null
	}
}

abstract class XMLPatternTocen<T : XMLTocen>(pattern: Pattern) : XMLTocen, XMLPatternTocenType<T>(pattern) {

	constructor(pattern: String) : this(Pattern.compile("^$pattern"))

	override fun newTocen(text: String): T = this as T
}

abstract class XMLPatternTocenType<T : XMLTocen>(private val pattern: Pattern) : XMLTocen.Type<T> {
	constructor(pattern: String) : this(Pattern.compile("^$pattern"))

	override fun find(text: CharSequence): Matcher<T>? {
		val m = pattern.matcher(text)
		if(m.find() && m.start() == 0)
			return Matcher(newTocen(text.substring(m.start(), m.end())), m.end())
		return null
	}

	abstract fun newTocen(text: String): T
}

abstract class XMLTextTocen<T : XMLTocen>(text: String) : XMLTocen, XMLTextTocenType<T>(text) {

	override fun newTocen(text: CharSequence): T = this as T
}

abstract class XMLTextTocenType<T : XMLTocen>(private val pattern: String) : XMLTocen.Type<T> {

	override fun find(text: CharSequence): Matcher<T>? {
		if(text.startsWith(pattern))
			return Matcher(
				newTocen(text.subSequence(0, pattern.length)),
				pattern.length
			)
		return null
	}

	abstract fun newTocen(text: CharSequence): T
}


