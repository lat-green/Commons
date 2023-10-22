package com.greentree.commons.reflection.soure;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Spliterators;
import java.util.jar.JarEntry;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public record BootStrupCodeSource() implements CodeSource {
	
	@Override
	public Stream<String> getAllClassesNames() {
		try {
			var url = new URL("jar:file:/" + System.getProperty("java.home") + "/lib/src.zip!/");
			var con = (JarURLConnection) url.openConnection();
			var file = con.getJarFile();
			var entries = file.entries().asIterator();
			return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entries, 0), false).map(JarEntry::toString)
					.map(x -> x.substring(x.indexOf('/') + 1)).map(x -> x.substring(0, x.length() - 5))
					.filter(x -> !x.endsWith("-info")).map(x -> x.replace('/', '.'));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}
	
}
