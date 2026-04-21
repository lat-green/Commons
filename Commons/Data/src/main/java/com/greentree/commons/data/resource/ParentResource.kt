package com.greentree.commons.data.resource

/**
 * Интерфейс ресурса, который может содержать дочерние ресурсы (файлы и директории).
 * Представляет собой директорию в абстрактной файловой системе.
 * 
 * @see ChildResource
 * @see RootResource
 * @see FolderResource
 */
interface ParentResource : Resource {

	/**
	 * Возвращает итератор по всем дочерним ресурсам.
	 */
	val children: Iterable<ChildResource>

	/**
	 * Возвращает дочерний ресурс по имени.
	 * @param name имя дочернего ресурса
	 * @return дочерний ресурс с указанным именем
	 */
	fun getChildren(name: String): ChildResource
}