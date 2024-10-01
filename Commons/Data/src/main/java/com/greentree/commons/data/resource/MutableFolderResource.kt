package com.greentree.commons.data.resource

interface MutableFolderResource : FolderResource, MutableChildResource, MutableParentResource {

	fun createFolder(): Boolean

	override fun getChildren(name: String): MutableChildResource
}