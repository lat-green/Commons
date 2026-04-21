package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource
import com.greentree.commons.data.resource.MutableParentResource
import com.greentree.commons.data.resource.MutableResource

/**
 * Интерфейс изменяемого местоположения ресурса.
 * Позволяет получать изменяемые ресурсы (MutableResource) по имени.
 * 
 * @see ResourceLocation
 * @see RootResourceLocation
 */
interface MutableResourceLocation : ResourceLocation {

	/**
	 * Возвращает изменяемый ресурс по имени.
	 * @param name имя ресурса
	 * @return MutableResource
	 */
	override fun getResource(name: String): MutableResource

	/**
	 * Возвращает изменяемый файл по имени.
	 * @param name имя файла
	 * @return MutableFileResource
	 */
	override fun getFileResource(name: String) = getResource(name) as MutableFileResource

	/**
	 * Возвращает изменяемую директорию по имени.
	 * @param name имя директории
	 * @return MutableFolderResource
	 */
	override fun getFolderResource(name: String) = getResource(name) as MutableFolderResource

	/**
	 * Возвращает изменяемый родительский ресурс по имени.
	 * @param name имя ресурса
	 * @return MutableParentResource
	 */
	override fun getParentResource(name: String) = getResource(name) as MutableParentResource
}
