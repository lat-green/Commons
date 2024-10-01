package com.greentree.commons.data.resource

data class ClassRootResource(
	val classLoader: ClassLoader,
) : MutableRootResource {

	constructor(cls: Class<*>) : this(cls.classLoader)

	override val children: Iterable<ChildResource>
		get() = TODO("Not yet implemented")

	override fun getChildren(name: String): ChildResource {
		TODO("Not yet implemented")
	}

	override val name: String
		get() = TODO("Not yet implemented")

	override fun lastModified(): Long {
		TODO("Not yet implemented")
	}

	override fun delete(): Boolean {
		TODO("Not yet implemented")
	}
}
