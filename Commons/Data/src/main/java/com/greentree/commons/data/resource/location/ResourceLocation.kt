package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.FolderResource
import com.greentree.commons.data.resource.ParentResource
import com.greentree.commons.data.resource.Resource
import java.io.Serializable

interface ResourceLocation : Serializable {

	fun getResource(name: String): Resource

	fun getFileResource(name: String) = getResource(name) as FileResource

	fun getFolderResource(name: String) = getResource(name) as FolderResource

	fun getParentResource(name: String) = getResource(name) as ParentResource
}

operator fun ResourceLocation.get(name: String) = getFileResource(name)