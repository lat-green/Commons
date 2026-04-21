package com.greentree.commons.data.resource

import com.greentree.commons.data.resource.location.RootUrlResourceLocation
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

/**
 * Реализация файлового ресурса на основе URL.
 * Позволяет читать файлы из интернета или других URL-источников.
 * 
 * @property url URL ресурса
 * @see FileResource
 */
data class URLFileResource(
	val url: URL,
) : FileResource {

	/**
	 * Конструктор из URI.
	 * @param uri URI ресурса
	 */
	constructor(uri: URI) : this(uri.toURL())

	/**
	 * Конструктор из строки.
	 * @param uri строковое представление URI
	 */
	constructor(uri: String) : this(URI(uri))

	/**
	 * Размер файла в байтах (определяется через HEAD-запрос).
	 * @return размер файла или -1, если неизвестно
	 */
	override val length: Long
		get() {
			val connection = url.openConnection()
			if(connection is HttpURLConnection)
				connection.requestMethod = "HEAD"
			return connection.contentLengthLong
		}

	/**
	 * Открывает поток ввода для чтения из URL.
	 * @return InputStream
	 */
	override fun open(): InputStream {
		val connection = url.openConnection()
		return connection.getInputStream()
	}

	override fun exists() = true

	/**
	 * Родительский ресурс (не реализовано).
	 */
	override val parent: ParentResource
		get() = TODO("Not yet implemented")
	/**
	 * Имя файла из URL.
	 */
	override val name: String
		get() = url.file

	/**
	 * Время последней модификации (определяется через HEAD-запрос).
	 * @return время в миллисекундах с начала эпохи
	 */
	override fun lastModified(): Long {
		val connection = url.openConnection()
		if(connection is HttpURLConnection)
			connection.requestMethod = "HEAD"
		return connection.lastModified
	}
}