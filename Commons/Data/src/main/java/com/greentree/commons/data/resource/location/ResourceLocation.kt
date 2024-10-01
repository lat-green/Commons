package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.FolderResource
import com.greentree.commons.data.resource.ParentResource
import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceNotFound
import java.io.Serializable

interface ResourceLocation : Serializable {

	fun getResource(name: String) = getResourceOrNull(name) ?: throw ResourceNotFound(name)
	fun getResourceOrNull(name: String): Resource?

	fun getFileResource(name: String) = getFileResourceOrNull(name) ?: throw ResourceNotFound(name)
	fun getFileResourceOrNull(name: String) = getResourceOrNull(name) as FileResource?

	fun getFolderResource(name: String) = getFolderResourceOrNull(name) ?: throw ResourceNotFound(name)
	fun getFolderResourceOrNull(name: String) = getResourceOrNull(name) as FolderResource?

	fun getParentResource(name: String) = getParentResourceOrNull(name) ?: throw ResourceNotFound(name)
	fun getParentResourceOrNull(name: String) = getResourceOrNull(name) as ParentResource?
}

operator fun ResourceLocation.get(name: String) = getFileResource(name)