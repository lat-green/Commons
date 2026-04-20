package com.greentree.commons.data.resource

interface FolderResource : ChildResource, ParentResource {

	override val isFile
		get() = false
	override val isDirectory
		get() = true
}