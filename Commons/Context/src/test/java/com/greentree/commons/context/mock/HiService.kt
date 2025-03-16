package com.greentree.commons.context.mock

data class HiService(
	val repository: HiRepository,
) {

	fun hello(): String {
		return "Service1 " + repository.hello()
	}
}
