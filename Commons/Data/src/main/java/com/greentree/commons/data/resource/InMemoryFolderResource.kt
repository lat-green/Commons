package com.greentree.commons.data.resource

import kotlin.math.max

class InMemoryFolderResource(
	override val name: String,
	files: Iterable<InMemoryResource>,
	private var parentResource: InMemoryFolderResource? = null,
) : MutableFolderResource, InMemoryResource {

	override val parent: ParentResource
		get() = parentResource ?: throw RuntimeException("parent not found")
	private val childrenResources = files.toMutableList()
	override val children: Iterable<MutableChildResource>
		get() = childrenResources
	private var lastModified: Long = System.currentTimeMillis()

	internal fun removeChild(file: MutableChildResource) {
		childrenResources.remove(file)
		lastModified = System.currentTimeMillis()
	}

	override fun createThisFolder() = false

	override fun getChildren(name: String) = childrenResources
		.firstOrNull { it.name == name } ?: InMemoryFileResource(name)
		.also {
			childrenResources.add(it)
			lastModified = System.currentTimeMillis()
		}

	override fun exists() = true

	override fun lastModified() = max(lastModified, childrenResources.maxOf { it.lastModified })

	override fun delete(): Boolean {
		if(childrenResources.isEmpty())
			return false
		childrenResources.forEach {
			it.delete()
		}
		childrenResources.clear()
		parentResource?.removeChild(this)
		parentResource = null
		lastModified = System.currentTimeMillis()
		return true
	}
}