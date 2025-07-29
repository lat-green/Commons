package test.com.greentree.commons.xml

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

data object XMLArgument : ArgumentsProvider {

	private val classLoader
		get() = XMLParserTest::class.java.classLoader

	override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
		return classLoader.getResourceAsStream("XMLs").use {
			String(it.readAllBytes())
		}
			.split('\n')
			.stream()
			.filter { it.isNotBlank() }
			.map { "XMLs/$it" }
			.map { ClassPathArguments(it) }
	}

	data class ClassPathArguments(val path: String) : Arguments {

		override fun get() = arrayOf(classLoader.getResourceAsStream(path))
	}
}