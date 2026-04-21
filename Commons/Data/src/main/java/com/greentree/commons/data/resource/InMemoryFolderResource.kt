package com.greentree.commons.data.resource

import kotlin.math.max

/**
 * Реализация директории, хранимой в памяти.
 * Содержит дочерние ресурсы (файлы и поддиректории) в оперативной памяти.
 *
 * @property name имя директории
 * @property files начальный список ресурсов
 * @property parentResource родительская директория (опционально)
 * @see MutableFolderResource
 * @see InMemoryResource
 */
class InMemoryFolderResource(
	override val name: String,
	files: Iterable<InMemoryResource>,
	private var parentResource: InMemoryFolderResource? = null,
) : MutableFolderResource, InMemoryResource {

	/**
	 * Родительский ресурс (родительская директория).
	 */
	override val parent: ParentResource
		get() = parentResource ?: throw RuntimeException("parent not found")
	private val childrenResources = files.toMutableList()

	/**
	 * Список дочерних ресурсов.
	 */
	override val children: Iterable<MutableChildResource>
		get() = childrenResources
	private var lastModified: Long = System.currentTimeMillis()

	/**
	 * Удаляет дочерний ресурс из директории.
	 * @param file ресурс для удаления
	 */
	internal fun removeChild(file: MutableChildResource) {
		childrenResources.remove(file)
		lastModified = System.currentTimeMillis()
	}

	/**
	 * Создает директорию (всегда возвращает false, так как директория в памяти
	 * всегда существует, если есть объект).
	 * @return false
	 */
	override fun createThisFolder() = false

	/**
	 * Возвращает или создает дочерний ресурс по имени.
	 * Если ресурс не найден, создает новый InMemoryFileResource.
	 * @param name имя дочернего ресурса
	 * @return дочерний ресурс
	 */
	override fun getChildren(name: String) = childrenResources
		.firstOrNull { it.name == name } ?: InMemoryFileResource(name)
		.also {
			childrenResources.add(it)
			lastModified = System.currentTimeMillis()
		}

	/**
	 * Проверяет, существует ли директория (всегда true).
	 * @return true
	 */
	override fun exists() = true

	/**
	 * Возвращает время последней модификации - максимальное время среди
	 * директории и всех её содержимых ресурсов.
	 * @return время в миллисекундах с начала эпохи
	 */
	override fun lastModified() = max(lastModified, childrenResources.maxOf { it.lastModified() })

	/**
	 * Устанавливает время последней модификации директории.
	 * @param time время в миллисекундах с начала эпохи
	 */
	override fun setLastModified(time: Long) {
		lastModified = time
	}

	/**
	 * Удаляет директорию и все её содержимое.
	 * @return true, если директория была удалена
	 */
	override fun delete(): Boolean {
		if(childrenResources.isEmpty())
			return false
		childrenResources.forEach {
			it.delete()
		}
		childrenResources.clear()
		parentResource?.removeChild(this)
		parentResource = null
		lastModified = System.currentTimeMillis()
		return true
	}
}