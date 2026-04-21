package com.greentree.commons.data.resource

/**
 * Интерфейс директории с возможностью записи.
 * Представляет папку, которую можно создавать, удалять и управлять её содержимым.
 *
 * @see FolderResource
 * @see MutableChildResource
 * @see MutableParentResource
 * @see SystemFileResource
 * @see InMemoryFolderResource
 */
interface MutableFolderResource : FolderResource, MutableChildResource, MutableParentResource {

	/**
	 * Создает эту директорию, если она не существует.
	 * @return true, если директория была создана; false, если директория уже существует
	 */
	fun createThisFolder(): Boolean

	/**
	 * Возвращает дочерний ресурс по имени (mutable версия).
	 * @param name имя дочернего ресурса
	 * @return mutable дочерний ресурс
	 */
	override fun getChildren(name: String): MutableChildResource
}