package com.greentree.commons.reflection.soure;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public record JarCodeSource(URL url) implements CodeSource {
	
	@Override
	public Stream<String> getAllClassesNames() {
		try {
			var con = (JarURLConnection) url.openConnection();
			var file = con.getJarFile();
			var entries = file.entries().asIterator();
			return StreamSupport
					.stream(Spliterators.spliteratorUnknownSize(entries, Spliterator.DISTINCT), false)
					.map(x -> x.getName()).map(x -> x.replace(".java", ".class")).filter(x -> x.endsWith(".class"))
					.map(x -> x.substring(0, x.length() - 6))
					.filter(x -> !x.endsWith("-info"))
					.map(x -> x.replace('\\', '.').replace('/', '.'));
		}catch(IOException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}
	
}
