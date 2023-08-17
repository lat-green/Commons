package com.greentree.commons.data.resource

import com.greentree.commons.util.exception.WrappedException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class URLResource(url: URL) : Resource {

	val url: URL

	init {
		this.url = Objects.requireNonNull(url)
	}

	override fun hashCode(): Int {
		return Objects.hash(url)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj == null || javaClass != obj.javaClass) return false
		val other = obj as URLResource
		return url == other.url
	}

	override fun toString(): String {
		return "URLResource [$url]"
	}

	override val name: String
		get() = url.file

	override fun length(): Long {
		return try {
			val connection = url.openConnection()
			if(connection is HttpURLConnection) connection.requestMethod = "HEAD"
			connection.contentLengthLong
		} catch(e: IOException) {
			throw WrappedException(e)
		}
	}

	override fun lastModified(): Long {
		return try {
			val connection = url.openConnection()
			if(connection is HttpURLConnection) connection.requestMethod = "HEAD"
			connection.lastModified
		} catch(e: IOException) {
			throw WrappedException(e)
		}
	}

	override fun open(): InputStream {
		return try {
			val c = url.openConnection()
			c.connect()
			c.getInputStream()
		} catch(e: IOException) {
			throw WrappedException(e)
		}
	}

	@Throws(MalformedURLException::class)
	override fun url(): URL {
		return url
	}

	override fun exists(): Boolean {
		return true
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}