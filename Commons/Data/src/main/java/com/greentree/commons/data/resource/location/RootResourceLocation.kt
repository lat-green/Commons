package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource

/**
 * Реализация MutableResourceLocation на основе MutableFolderResource.
 * Делегирует получение ресурсов родительской директории.
 * 
 * @property resource MutableFolderResource
 * @see MutableResourceLocation
 */
class RootResourceLocation(
	val resource: MutableFolderResource,
) : MutableResourceLocation {

	/**
	 * Возвращает MutableFileResource по имени.
	 * @param name имя ресурса
	 * @return MutableFileResource
	 */
	override fun getResource(name: String) = resource.getChildren(name) as MutableFileResource
}