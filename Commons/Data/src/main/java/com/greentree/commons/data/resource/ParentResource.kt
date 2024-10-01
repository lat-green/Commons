package com.greentree.commons.data.resource

interface ParentResource : Resource {

	val children: Iterable<ChildResource>

	fun getChildren(name: String): ChildResource
}