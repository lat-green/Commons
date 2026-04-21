package com.greentree.commons.data.resource

import com.greentree.commons.data.asGathering
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.GatheringByteChannel
import java.nio.channels.WritableByteChannel
import java.nio.charset.Charset

/**
 * Интерфейс файлового ресурса с возможностью записи.
 * Представляет файл, который можно создавать, записывать и удалять.
 *
 * @see FileResource
 * @see MutableResource
 * @see MutableChildResource
 * @see SystemFileResource
 * @see InMemoryFileResource
 */
interface MutableFileResource : FileResource, MutableResource, MutableChildResource {

	/**
	 * Создает новый файл, если он не существует.
	 * @return true, если файл был создан; false, если файл уже существует
	 */
	fun createThisFile(): Boolean

	/**
	 * Открывает файл для записи и возвращает поток вывода.
	 * @return OutputStream для записи в файл
	 */
	fun openWrite(): OutputStream

	/**
	 * Открывает канал для записи в файл.
	 * @return GatheringByteChannel для записи
	 */
	fun openWriteChannel(): GatheringByteChannel = Channels.newChannel(openWrite()).asGathering
}

/**
 * Записывает текст в канал с использованием указанной кодировки.
 * @param text текст для записи
 * @param charset кодировка (по умолчанию UTF-8)
 */
fun WritableByteChannel.writeText(text: String, charset: Charset = Charsets.UTF_8) =
	writeBytes(text.toByteArray(charset))

/**
 * Записывает байты в канал.
 * @param array массив байтов для записи
 */
fun WritableByteChannel.writeBytes(array: ByteArray) =
	write(ByteBuffer.wrap(array))

/**
 * Записывает текст в файл с использованием указанной кодировки.
 * @param text текст для записи
 * @param charset кодировка (по умолчанию UTF-8)
 */
fun MutableFileResource.writeText(text: String, charset: Charset = Charsets.UTF_8) =
	writeBytes(text.toByteArray(charset))

/**
 * Записывает байты в файл.
 * @param array массив байтов для записи
 */
fun MutableFileResource.writeBytes(array: ByteArray) = openWrite().use { it.write(array) }

/**
 * Записывает часть байтового массива в файл.
 * @param array массив байтов
 * @param off начальный индекс
 * @param len количество байтов для записи
 */
fun MutableFileResource.writeBytes(array: ByteArray, off: Int, len: Int) = openWrite().use { it.write(array, off, len) }

/**
 * Копирует содержимое файла в другой файл, если файл был модифицирован после указанного времени.
 * @param result целевой файл для записи
 * @param lastRead время последнего чтения исходного файла
 */
fun FileResource.writeTo(result: MutableFileResource, lastRead: Long) {
	val m = lastModified()
	if(m == 0L || m > lastRead)
		writeTo(result)
}

/**
 * Копирует содержимое файла в другой файл.
 * @param result целевой файл для записи
 */
fun FileResource.writeTo(result: MutableFileResource) {
	result.openWrite().use { out -> open().use { `in` -> `in`.transferTo(out) } }
}