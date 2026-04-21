package com.greentree.commons.data.resource

/**
 * Интерфейс ресурса, который можно изменять (удалять, менять время модификации).
 * Представляет собой файл или директорию с возможностью записи.
 * 
 * @see MutableChildResource
 * @see MutableParentResource
 * @see MutableFileResource
 * @see MutableFolderResource
 */
interface MutableResource : Resource {

	/**
	 * Устанавливает время последней модификации ресурса.
	 * @param time время в миллисекундах с начала эпохи
	 */
	fun setLastModified(time: Long)

	/**
	 * Удаляет ресурс.
	 * @return true, если ресурс успешно удален
	 */
	fun delete(): Boolean
}
