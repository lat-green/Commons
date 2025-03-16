package com.greentree.commons.context.mock

class TextRepository(val text: String) : HiRepository {

	override fun hello(): String {
		return "Hi $text"
	}
}
