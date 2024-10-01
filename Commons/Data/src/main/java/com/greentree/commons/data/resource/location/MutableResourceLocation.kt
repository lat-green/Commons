package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource
import com.greentree.commons.data.resource.MutableParentResource
import com.greentree.commons.data.resource.MutableResource
import com.greentree.commons.data.resource.ResourceNotFound

interface MutableResourceLocation : ResourceLocation {

	override fun getResource(name: String) = getResourceOrNull(name) ?: throw ResourceNotFound(name)
	override fun getResourceOrNull(name: String): MutableResource?

	override fun getFileResource(name: String) = getFileResourceOrNull(name) ?: throw ResourceNotFound(name)
	override fun getFileResourceOrNull(name: String) = getResourceOrNull(name) as MutableFileResource?

	override fun getFolderResource(name: String) = getFolderResourceOrNull(name) ?: throw ResourceNotFound(name)
	override fun getFolderResourceOrNull(name: String) = getResourceOrNull(name) as MutableFolderResource?

	override fun getParentResource(name: String) = getParentResourceOrNull(name) ?: throw ResourceNotFound(name)
	override fun getParentResourceOrNull(name: String) = getResourceOrNull(name) as MutableParentResource?
}
