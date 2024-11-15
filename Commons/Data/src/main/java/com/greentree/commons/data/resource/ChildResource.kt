package com.greentree.commons.data.resource

interface ChildResource : Resource {

	val parent: ParentResource

	fun exists(): Boolean
}