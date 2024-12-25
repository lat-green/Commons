package com.greentree.commons.data

import java.io.InputStream

data class ByteSequenceInputStream(
	val sequence: Iterator<Byte>,
) : InputStream() {

	constructor(sequence: Sequence<Byte>) : this(sequence.iterator())
	constructor(iterable: Iterable<Byte>) : this(iterable.iterator())

	override fun read() = if(sequence.hasNext())
		sequence.next().toInt()
	else
		-1
}