package com.greentree.commons.data.resource

interface RootResource : ParentResource {

	override fun exists() = true

	override val isFile: Boolean
		get() = false
	override val isDirectory: Boolean
		get() = true
}