package com.greentree.commons.xml

import java.io.File
import java.io.InputStream
import java.nio.file.Path
import kotlin.io.path.inputStream

fun interface XMLParser {

	fun parse(inputStream: InputStream): XMLElement
}

fun XMLParser.parse(text: String): XMLElement = text.byteInputStream().use { parse(it) }
fun XMLParser.parse(file: File): XMLElement = file.inputStream().use { parse(it) }
fun XMLParser.parse(path: Path): XMLElement = path.inputStream().use { parse(it) }

