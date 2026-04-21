package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.data.asScattering
import com.greentree.commons.data.channel.AsyncByteChannel
import com.greentree.commons.data.channel.AsyncByteChannelWrapper
import java.io.InputStream
import java.nio.channels.Channels
import java.nio.channels.ScatteringByteChannel

/**
 * Интерфейс файлового ресурса в абстрактной файловой системе.
 * Представляет собой файл, который можно читать и (опционально) записывать.
 * 
 * @see ChildResource
 * @see FileResource
 * @see MutableFileResource
 * @see SystemFileResource
 * @see URLFileResource
 * @see InMemoryFileResource
 */
interface FileResource : ChildResource {

	/**
	 * Расширение имени файла (часть имени после последней точки).
	 * @return расширение файла или пустая строка, если точка отсутствует
	 */
	val extension: String
		get() = name.substringAfterLast('.')

	/**
	 * Размер файла в байтах.
	 * @return размер файла или -1, если размер неизвестен
	 */
	/**
	 * @return length or -1 if unknown
	 */
	val length: Long

	/**
	 * Файл является файлом (не директорией).
	 * @return true
	 */
	override val isFile
		get() = true
	/**
	 * Файл не является директорией.
	 * @return false
	 */
	override val isDirectory
		get() = false

	/**
	 * Открывает файл для чтения и возвращает поток ввода.
	 * @return InputStream для чтения из файла
	 */
	fun open(): InputStream

	/**
	 * Открывает канал для чтения из файла.
	 * @return ScatteringByteChannel для чтения
	 */
	fun openChannel(): ScatteringByteChannel = Channels.newChannel(open()).asScattering
	/**
	 * Открывает асинхронный канал для чтения из файла.
	 * @return AsyncByteChannel для асинхронного чтения
	 */
	fun openAsyncChannel(): AsyncByteChannel = AsyncByteChannelWrapper(openChannel())

	/**
	 * Событие, возникающее при создании файла.
	 */
	val onCreate: RunObservable
		get() = TODO("Not yet implemented")
	/**
	 * Событие, возникающее при модификации файла.
	 */
	val onModify: RunObservable
		get() = TODO("Not yet implemented")
	/**
	 * Событие, возникающее при удалении файла.
	 */
	val onDelete: RunObservable
		get() = TODO("Not yet implemented")
}

/**
 * Добавляет слушатель события создания файла.
 * @param listener слушатель, который будет вызван при создании файла
 */
fun FileResource.onCreate(listener: Runnable) = onCreate.addListener(listener)
/**
 * Добавляет слушатель события модификации файла.
 * @param listener слушатель, который будет вызван при модификации файла
 */
fun FileResource.onModify(listener: Runnable) = onModify.addListener(listener)
/**
 * Добавляет слушатель события удаления файла.
 * @param listener слушатель, который будет вызван при удалении файла
 */
fun FileResource.onDelete(listener: Runnable) = onDelete.addListener(listener)
