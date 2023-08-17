package com.greentree.commons.data.resource

import java.io.IOException
import java.io.OutputStream

interface IOResource : Resource {

	/**
	 * @return `true` if and only if the file or directory is successfully
	 * deleted; `false` otherwise
	 */
	fun delete(): Boolean

	fun openWrite(): OutputStream

	object Null : IOResource, Resource by Resource.Null {

		override fun delete() = true

		override fun openWrite(): OutputStream {
			throw IOException("resource not exists")
		}
	}
}