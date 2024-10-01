package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource

class RootResourceLocation(
	val resource: MutableFolderResource,
) : MutableResourceLocation {

	override fun getResource(name: String) = resource.getChildren(name) as MutableFileResource

	override fun getResourceOrNull(name: String) = resource.getChildren(name) as MutableFileResource
}