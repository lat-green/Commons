package com.greentree.commons.xml.parser.token

import java.util.*

private fun removeUnUsed(text: CharSequence, builder: StringBuilder = StringBuilder()): StringBuilder {
	run {
		val index = text.indexOf("<!--")
		if(index != -1) {
			val e = text.indexOf("-->", index)
			removeUnUsed(text.substring(0, index), builder)
			removeUnUsed(text.substring(e + 3), builder)
			return builder
		}
	}
	run {
		val index = text.indexOf("<?")
		if(index != -1) {
			val e = text.indexOf("?>", index)
			removeUnUsed(text.substring(0, index), builder)
			removeUnUsed(text.substring(e + 2), builder)
			return builder
		}
	}
	builder.append(text)
	return builder
}

class XMLScanner(code: String) : Iterator<XMLTocen> {

	private var code: CharSequence =
		removeUnUsed(code).toString()

	override fun hasNext(): Boolean {
		return 0 < code.length || next.isNotEmpty()
	}

	private val next: Queue<XMLTocen> = LinkedList()

	override fun next(): XMLTocen {
		if(next.isNotEmpty())
			return next.remove()
		val matcher = xmlTocenTypes
			.mapNotNull {
				it.find(code)
			}
			.minByOrNull {
				it.end
			} ?: throw NoSuchElementException("$code")
		code = code.subSequence(matcher.end ..< code.length)
		next.addAll(matcher.token)
		return next.remove()
	}
}
