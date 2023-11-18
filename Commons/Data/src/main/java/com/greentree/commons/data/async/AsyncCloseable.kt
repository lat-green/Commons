package com.greentree.commons.data.async

interface AsyncCloseable {

	suspend fun close()
}

suspend inline fun <T : AsyncCloseable, R> T.use(block: (T) -> R): R {
	try {
		return block(this)
	} finally {
		close()
	}
}

