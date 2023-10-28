package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.URLResource
import java.net.MalformedURLException
import java.net.URL

class RootUrlResourceLocation(private val context: URL) : ResourceLocation {

	override val lastModified: Long
		get() = URLResource(context).lastModified()

	override fun getResource(name: String) = URLResource(
		try {
			URL(context, name)
		} catch(e: MalformedURLException) {
			throw RuntimeException(e)
		}
	)
}