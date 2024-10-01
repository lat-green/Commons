package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.ResourceNotFound
import com.greentree.commons.data.resource.URLFileResource
import java.net.MalformedURLException
import java.net.URL

data class RootUrlResourceLocation(
	private val context: URL,
) : ResourceLocation {

	override fun getResourceOrNull(name: String) = URLFileResource(
		try {
			URL(context, name)
		} catch(e: MalformedURLException) {
			throw ResourceNotFound(e)
		}
	)
}