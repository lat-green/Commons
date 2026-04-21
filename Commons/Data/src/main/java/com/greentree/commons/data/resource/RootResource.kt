package com.greentree.commons.data.resource

/**
 * Интерфейс корневого ресурса (корневой директории) в абстрактной файловой системе.
 * Корневой ресурс всегда существует и является директорией.
 * 
 * @see ParentResource
 * @see MutableRootResource
 */
interface RootResource : ParentResource {

	/**
	 * Корневой ресурс всегда существует.
	 * @return true
	 */
	override fun exists() = true

	/**
	 * Корневой ресурс не является файлом.
	 * @return false
	 */
	override val isFile: Boolean
		get() = false
	/**
	 * Корневой ресурс является директорией.
	 * @return true
	 */
	override val isDirectory: Boolean
		get() = true
}