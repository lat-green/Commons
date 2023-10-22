package com.greentree.commons.reflection.soure;

import java.net.URL;
import java.util.stream.Stream;

public interface CodeSource {
	
	Stream<String> getAllClassesNames();
	
	default Stream<Class<?>> getAllClasses() {
		return getAllClassesNames().<Class<?>>map(x -> {
			try {
				return Class.forName(x, false, CodeSource.class.getClassLoader());
			}catch(ClassNotFoundException e) {
				return null;
			}catch(NoClassDefFoundError e) {
				return null;
			}
		}).filter(x -> x != null);
	}
	
	static CodeSource create(URL url) {
		var protocol = url.getProtocol();
		return switch(protocol) {
			case "file" -> new PathCodeSource(url);
			case "jar" -> new JarCodeSource(url);
			default ->
				throw new IllegalArgumentException("Unexpected value: " + protocol);
		};
	}
	
}
