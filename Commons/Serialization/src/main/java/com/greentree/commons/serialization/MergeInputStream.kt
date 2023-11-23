package com.greentree.commons.serialization

import java.io.InputStream

class MergeInputStream(val a: InputStream, val b: InputStream) : InputStream() {

	override fun read(): Int {
		val r = a.read()
		if(r == -1)
			return b.read()
		return r
	}
}
