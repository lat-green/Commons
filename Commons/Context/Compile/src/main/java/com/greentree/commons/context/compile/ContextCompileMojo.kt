package com.greentree.commons.context.compile

import org.apache.maven.execution.MavenSession
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.BuildPluginManager
import org.apache.maven.plugins.annotations.Component
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.ResolutionScope
import org.apache.maven.project.MavenProject

@Mojo(
	name = "compile",
	defaultPhase = LifecyclePhase.PROCESS_CLASSES,
	requiresDependencyResolution = ResolutionScope.COMPILE
)
class ContextCompileMojo : AbstractMojo() {

	@Component
	lateinit var mavenProject: MavenProject

	@Component
	lateinit var mavenSession: MavenSession

	@Component
	lateinit var pluginManager: BuildPluginManager

	override fun execute() {
		val classLoader = getClassLoader(mavenProject)
		val allBeanLayerClasses = getAllClasses(
			listOf(mavenProject.build.outputDirectory),
			classLoader
		)
		//.filter { ClassUtil.isExtends(BeanLayer::class.java, it) }
//		println(ClassFile.of().parse())
		println(allBeanLayerClasses)
	}
}