package com.greentree.commons.data.async

suspend fun AsyncInputStream.transferTo(out: AsyncOutputStream): Long {
	var transferred: Long = 0
	val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
	var read: Int
	while(read(buffer, 0, DEFAULT_BUFFER_SIZE).also { read = it } >= 0) {
		out.write(buffer, 0, read)
		if(transferred < Long.MAX_VALUE) {
			transferred = try {
				Math.addExact(transferred, read.toLong())
			} catch(ignore: ArithmeticException) {
				Long.MAX_VALUE
			}
		}
	}
	return transferred
}