package com.greentree.commons.data.resource

interface Resource {

	val name: String

	fun lastModified(): Long
}

val Resource.lastModified
	get() = lastModified()