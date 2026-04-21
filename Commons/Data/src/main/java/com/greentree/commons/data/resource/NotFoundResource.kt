package com.greentree.commons.data.resource

import java.io.InputStream
import java.io.OutputStream

/**
 * Реализация ресурса, который не найден (пустышка).
 * Используется для представления отсутствующих файлов/директорий.
 * Все операции выбрасывают исключение ResourceNotFound.
 *
 * @property name имя ресурса
 * @see ResourceNotFound
 * @see MutableFileResource
 * @see MutableFolderResource
 * @see MutableRootResource
 */
data class NotFoundResource(
	override val name: String,
) : MutableFileResource, MutableFolderResource, MutableRootResource {

	private fun throwNotFound(): Nothing {
		throw ResourceNotFound(name)
	}

	/**
	 * Удаление ресурса всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun delete(): Boolean {
		throwNotFound()
	}

	/**
	 * Получение времени модификации всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun lastModified(): Long {
		throwNotFound()
	}

	/**
	 * Установка времени модификации всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun setLastModified(time: Long) {
		throwNotFound()
	}

	/**
	 * Создание файла всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun createThisFile(): Boolean {
		throwNotFound()
	}

	/**
	 * Открытие для записи всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun openWrite(): OutputStream {
		throwNotFound()
	}

	/**
	 * Получение длины файла всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override val length: Long
		get() = throwNotFound()

	/**
	 * Открытие для чтения всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun open(): InputStream {
		throwNotFound()
	}

	/**
	 * Получение родителя всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override val parent: ParentResource
		get() = throwNotFound()

	/**
	 * Проверка существования ресурса.
	 * @return false - ресурс не найден
	 */
	override fun exists(): Boolean {
		return false
	}

	/**
	 * Проверка, является ли ресурс файлом, всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override val isFile
		get() = throwNotFound()

	/**
	 * Проверка, является ли ресурс директорией, всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override val isDirectory
		get() = throwNotFound()

	/**
	 * Создание директории всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun createThisFolder(): Boolean {
		throwNotFound()
	}

	/**
	 * Получение дочернего ресурса всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override fun getChildren(name: String): MutableChildResource {
		throwNotFound()
	}

	/**
	 * Получение списка дочерних ресурсов всегда выбрасывает исключение.
	 * @throws ResourceNotFound всегда
	 */
	override val children: Iterable<ChildResource>
		get() = throwNotFound()
}