/**
 * Утилиты для чтения данных из файловых ресурсов.
 * Предоставляет расширения для чтения байтов, текста и работы с каналами.
 *
 * @see FileResource
 * @see MutableFileResource
 */
package com.greentree.commons.data.resource

import java.io.Reader
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset

/**
 * Читает все байты из файлового ресурса.
 * @return массив байт всего файла
 */
fun FileResource.readBytes() = open().use { it.readBytes() }

/**
 * Создает Reader для чтения текста из файла с указанной кодировкой.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return Reader для чтения
 */
fun FileResource.reader(charset: Charset = Charsets.UTF_8) = open().reader(charset)

/**
 * Создает BufferedReader для чтения текста из файла.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return BufferedReader для чтения
 */
fun FileResource.bufferedReader(charset: Charset = Charsets.UTF_8) = open().bufferedReader(charset)

/**
 * Читает весь текст из файла.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return содержимое файла в виде строки
 */
fun FileResource.readText(charset: Charset = Charsets.UTF_8) = bufferedReader(charset).use { it.readText() }

/**
 * Создает Reader для чтения текста из канала.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return Reader для чтения
 */
fun ReadableByteChannel.reader(charset: Charset = Charsets.UTF_8): Reader = Channels.newReader(this, charset)

/**
 * Читает весь текст из канала.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return содержимое в виде строки
 */
fun ReadableByteChannel.readText(charset: Charset = Charsets.UTF_8) = reader(charset).use { it.readText() }

/**
 * Асинхронно читает текст из файла.
 * @param charset кодировка (по умолчанию UTF-8)
 * @return содержимое файла в виде строки
 */
suspend fun FileResource.readTextAsync(charset: Charset = Charsets.UTF_8) = String(readBytesAsync(), charset)

/**
 * Асинхронно читает все байты из файла.
 * @return массив байт всего файла
 */
suspend fun FileResource.readBytesAsync(): ByteArray {
	openAsyncChannel().use { byteChannel ->
		val fullSize = (if(length == -1L) byteChannel.size else length).toInt()
		val result = ByteArray(fullSize)
		val buffer = ByteBuffer.wrap(result)
		byteChannel.read(buffer, 0)
		return result
	}
}
