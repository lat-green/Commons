package com.greentree.commons.data.resource

/**
 * Базовый интерфейс для представления ресурса (файла или директории) в абстрактной файловой системе.
 * Предоставляет унифицированный API для работы с различными источниками данных
 * (файловая система, память, URL, JAR-ресурсы и т.д.).
 *
 * @see ChildResource
 * @see ParentResource
 * @see FileResource
 * @see FolderResource
 */
sealed interface Resource {

	/**
	 * Имя ресурса (имя файла или директории).
	 */
	val name: String

	/**
	 * Проверяет существование ресурса.
	 * @return true, если ресурс существует
	 */
	fun exists(): Boolean

	/**
	 * Является ли ресурс файлом.
	 * @return true, если ресурс является файлом
	 */
	val isFile: Boolean

	/**
	 * Является ли ресурс директорией.
	 * @return true, если ресурс является директорией
	 */
	val isDirectory: Boolean

	/**
	 * Возвращает время последней модификации ресурса в миллисекундах с начала эпохи.
	 * @return время последней модификации или 0, если время неизвестно
	 */
	fun lastModified(): Long
}

/**
 * Расширение имени файла (часть имени после последней точки).
 * @return расширение файла или пустая строка, если точка отсутствует
 */
val Resource.extension
	get() = name.substringAfterLast('.')

/**
 * Рекурсивно обходит все файлы в ресурсе (если это директория) и вызывает
 * указанную функцию для каждого файла.
 * @param block функция, которая будет вызвана для каждого файла
 */
fun Resource.walk(block: (file: FileResource) -> Unit) {
	return when(this) {
		is ParentResource -> for(child in children) {
			child.walk(block)
		}

		is FileResource -> block(this)
		else -> TODO("$this")
	}
}