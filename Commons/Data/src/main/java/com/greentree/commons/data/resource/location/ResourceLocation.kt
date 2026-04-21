package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.FolderResource
import com.greentree.commons.data.resource.ParentResource
import com.greentree.commons.data.resource.Resource
import java.io.Serializable

/**
 * Базовый интерфейс для представления местоположения ресурса.
 * Позволяет получать ресурсы по имени из различных источников
 * (файловая система, память, URL и т.д.).
 * 
 * @see MutableResourceLocation
 * @see NamedResourceLocation
 * @see RootResourceLocation
 */
interface ResourceLocation : Serializable {

	/**
	 * Возвращает ресурс по имени.
	 * @param name имя ресурса
	 * @return ресурс с указанным именем
	 */
	fun getResource(name: String): Resource

	/**
	 * Возвращает файловый ресурс по имени.
	 * @param name имя файла
	 * @return FileResource
	 */
	fun getFileResource(name: String) = getResource(name) as FileResource

	/**
	 * Возвращает директорию по имени.
	 * @param name имя директории
	 * @return FolderResource
	 */
	fun getFolderResource(name: String) = getResource(name) as FolderResource

	/**
	 * Возвращает родительский ресурс по имени.
	 * @param name имя ресурса
	 * @return ParentResource
	 */
	fun getParentResource(name: String) = getResource(name) as ParentResource
}

/**
 * Оператор для удобного доступа к ресурсам по имени.
 * @param name имя ресурса
 * @return FileResource
 */
operator fun ResourceLocation.get(name: String) = getFileResource(name)