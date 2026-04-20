package com.greentree.commons.data.resource

interface MutableResource : Resource {

	fun setLastModified(time: Long)
	fun delete(): Boolean
}
