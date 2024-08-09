package com.greentree.commons.data.resource

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

data class URLResource(
	private val url: URL,
) : Resource {

	override fun toString(): String {
		return "URLResource [$url]"
	}

	override val name: String
		get() = url.file

	override fun length(): Long {
		val connection = url.openConnection()
		if(connection is HttpURLConnection)
			connection.requestMethod = "HEAD"
		return connection.contentLengthLong
	}

	override fun lastModified(): Long {
		val connection = url.openConnection()
		if(connection is HttpURLConnection)
			connection.requestMethod = "HEAD"
		return connection.lastModified
	}

	override fun open(): InputStream {
		val c = url.openConnection()
		c.connect()
		return c.getInputStream()
	}

	override fun url(): URL {
		return url
	}

	override fun exists(): Boolean {
		return true
	}
}