package com.greentree.commons.data.resource

interface MutableResource : Resource {

	fun delete(): Boolean
}
