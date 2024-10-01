package com.greentree.commons.data.resource.location

import com.greentree.commons.graph.tree
import com.greentree.commons.util.iterator.yieldAll
import java.io.File
import java.nio.file.Path

fun RecursionFileSystemLocation(path: Path) = RecursionFileSystemLocation(path.toFile())

fun RecursionFileSystemLocation(root: File) = tree(root) { file ->
	if(file.isDirectory)
		yieldAll(file.listFiles())
}.filter { it.isDirectory }.map { RootFileResourceLocation(it) }
	.reduce<NamedResourceLocation, NamedResourceLocation> { a, b -> a + b }