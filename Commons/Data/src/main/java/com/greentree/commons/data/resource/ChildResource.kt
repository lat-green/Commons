package com.greentree.commons.data.resource

/**
 * Интерфейс ресурса, который имеет родительский ресурс.
 * Представляет собой файл или директорию внутри какой-либо директории.
 * 
 * @see ParentResource
 * @see FileResource
 * @see FolderResource
 */
interface ChildResource : Resource {

	/**
	 * Родительский ресурс (директория, в которой находится этот ресурс).
	 */
	val parent: ParentResource
}