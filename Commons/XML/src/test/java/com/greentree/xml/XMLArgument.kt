package com.greentree.xml

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.io.InputStream
import java.util.stream.Stream

data object XMLArgument : ArgumentsProvider {

	override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
		val streams: MutableCollection<InputStream> = ArrayList()
		val cl = LXMLParserTest::class.java.classLoader
		var i = 0
		while(true) {
			val s = cl.getResourceAsStream("XMLs/$i.xml") ?: break
			streams.add(s)
			i++
		}
		return streams.stream().map { Arguments.of(it) }
	}
}