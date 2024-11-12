package com.greentree.commons.data.resource

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path

object Resources {

	@JvmStatic
	fun of(file: File) = SystemFileResource(file)

	@JvmStatic
	fun of(path: Path) = of(path.toFile())

	@JvmStatic
	fun of(name: String, text: String, charset: Charset = Charsets.UTF_8) =
		InMemoryFileResource(name, text.toByteArray(charset))

	@JvmStatic
	fun of(cls: Class<*>) = ClassRootResource(cls)
}