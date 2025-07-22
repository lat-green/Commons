package com.greentree.commons.xml.parser

import org.antlr.v4.runtime.ANTLRErrorListener
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import java.util.*

object ThrowANTLRErrorListener : ANTLRErrorListener {

	override fun syntaxError(
		recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInLine: Int,
		msg: String, e: RecognitionException,
	) {
		throw RuntimeException("$msg line:$line charPositionInLine:$charPositionInLine", e)
	}

	override fun reportAmbiguity(
		recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int, exact: Boolean,
		ambigAlts: BitSet, configs: ATNConfigSet,
	) {
		throw RuntimeException()
	}

	override fun reportAttemptingFullContext(
		recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int,
		conflictingAlts: BitSet, configs: ATNConfigSet,
	) {
		throw RuntimeException()
	}

	override fun reportContextSensitivity(
		recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int, prediction: Int,
		configs: ATNConfigSet,
	) {
		throw RuntimeException()
	}
}