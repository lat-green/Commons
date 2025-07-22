package com.greentree.commons.xml.parser

import com.greentree.commons.xml.XMLElement
import com.greentree.commons.xml.XMLParserBaseVisitor
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

object ANTLR4XMLParser : XMLParser {

	override fun parse(inputStream: InputStream): XMLElement {
		val stream = CharStreams.fromStream(inputStream, StandardCharsets.UTF_8)
		val lexer = com.greentree.commons.xml.XMLLexer(stream)
		lexer.removeErrorListeners()
		lexer.addErrorListener(ThrowANTLRErrorListener)
		val tokens = CommonTokenStream(lexer)
		val parser = com.greentree.commons.xml.XMLParser(tokens)
		val element = parser.document().element()
		return XMLElementVisitor.visit(element)
	}
}

private object XMLElementVisitor : XMLParserBaseVisitor<XMLElement>() {

	override fun visitElement(ctx: com.greentree.commons.xml.XMLParser.ElementContext): XMLElement {
		val name = ctx.Name(0).text
		val children = ctx.content()?.element()?.map { visit(it) } ?: listOf()
		val content = ctx.content()?.let { TextVisitor.visit(it) }?.replace("\r", "")?.trim() ?: ""
		val attributes = ctx.attribute().associate { it.Name().text to (it.STRING()?.text?.trimQuotes() ?: "") }
			.filterValues { it.isNotBlank() }

		return XMLElement(children, attributes, name, content)
	}

	override fun aggregateResult(aggregate: XMLElement?, nextResult: XMLElement?): XMLElement? {
		if(aggregate == null)
			return nextResult
		if(nextResult == null)
			return aggregate
		TODO("$aggregate $nextResult")
	}
}

private object TextVisitor : XMLParserBaseVisitor<String>() {

	const val HEXDIGIT_PREFIX = "&#x"
	const val DIGIT_PREFIX = "&#"

	override fun visitChardata(ctx: com.greentree.commons.xml.XMLParser.ChardataContext): String {
		return ctx.text
	}

	override fun visitReference(ctx: com.greentree.commons.xml.XMLParser.ReferenceContext): String {
		val text = ctx.text
		ctx.CharRef()?.let {
			when {
				text.startsWith(HEXDIGIT_PREFIX) -> return text.substring(HEXDIGIT_PREFIX.length, text.length - 1)
					.toInt(16).toChar().toString()

				text.startsWith(DIGIT_PREFIX) -> return text.substring(DIGIT_PREFIX.length, text.length - 1).toInt(10)
					.toChar().toString()

				else -> TODO("$ctx")
			}
		}
		ctx.EntityRef()?.let {
			val name = text.substring(1, text.length - 1)
			return MyXMLParser.getByName(name).toString()
		}
		return ctx.text
	}

	override fun visitElement(ctx: com.greentree.commons.xml.XMLParser.ElementContext): String {
		return ""
	}

	override fun aggregateResult(aggregate: String?, nextResult: String?): String? {
		if(aggregate == null)
			return nextResult
		if(nextResult == null)
			return aggregate
		return aggregate + nextResult
	}
}

private fun String.trimQuotes() = substring(1, length - 1)