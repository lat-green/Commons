package com.greentree.commons.data.resource

interface MutableFolderResource : FolderResource, MutableChildResource, MutableParentResource {

	fun createThisFolder(): Boolean

	override fun getChildren(name: String): MutableChildResource
}