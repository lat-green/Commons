package com.greentree.commons.context.compile

import org.apache.maven.project.MavenProject
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt

fun getClassLoader(project: MavenProject): ClassLoader {
	val classPathUrls = mutableListOf<URL>()
	val artifacts = project.dependencyArtifacts
	for(artifact in artifacts) {
		artifact?.file?.toURI()?.toURL()?.let {
			classPathUrls.add(it)
		}
	}
	val classpathElements = project.compileClasspathElements
	classpathElements.add(project.build.outputDirectory)
	classpathElements.add(project.build.testOutputDirectory)
	for(classpathElement in classpathElements) {
		classPathUrls.add(File(classpathElement).toURI().toURL())
	}
	return URLClassLoader(classPathUrls.toTypedArray(), UtilKt::class.java.classLoader)
}

fun getAllClasses(
	project: MavenProject,
	loader: ClassLoader = getClassLoader(project),
) = getAllClasses(project.compileClasspathElements, loader)

fun getAllClasses(
	compileClasspathElements: List<String>,
	loader: ClassLoader,
) = run {
	compileClasspathElements.map { File(it) }.flatMap { file ->
		if(file.isDirectory)
			getAllFiles(file).mapNotNull {
				runCatching { it.absolutePath.substring(file.absolutePath.length + 1) }.getOrNull()
			}.map { "${file}/${it}" }
		else {
			TODO()
		}
	}.filter { it.endsWith(".class") && "-" !in it }
}

fun getAllFiles(file: File): List<File> =
	if(file.isDirectory)
		file.listFiles().flatMap { getAllFiles(it.canonicalFile) }
	else
		listOf(file)

inline fun <E, R> Iterable<E>.mapNotThrow(block: (E) -> R) =
	mapNotNull { runCatching { block(it) }.getOrNull() }