package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource
import com.greentree.commons.data.resource.MutableParentResource
import com.greentree.commons.data.resource.MutableResource

interface MutableResourceLocation : ResourceLocation {

	override fun getResource(name: String): MutableResource

	override fun getFileResource(name: String) = getResource(name) as MutableFileResource

	override fun getFolderResource(name: String) = getResource(name) as MutableFolderResource

	override fun getParentResource(name: String) = getResource(name) as MutableParentResource
}
