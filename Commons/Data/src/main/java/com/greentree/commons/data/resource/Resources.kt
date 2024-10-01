package com.greentree.commons.data.resource

import java.io.File
import java.nio.file.Path

object Resources {

	@JvmStatic
	fun of(file: File) = SystemFileResource(file)

	@JvmStatic
	fun of(path: Path) = of(path.toFile())

	@JvmStatic
	fun of(cls: Class<*>) = ClassRootResource(cls)
}