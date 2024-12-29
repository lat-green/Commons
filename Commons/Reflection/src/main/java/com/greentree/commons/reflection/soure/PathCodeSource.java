package com.greentree.commons.reflection.soure;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public record PathCodeSource(URL url) implements CodeSource {
	
	@Override
	public Stream<String> getAllClassesNames() {
		try {
			var file = Paths.get(url.toURI()).getParent().toFile();
			var length = file.getAbsolutePath().length() + 1;
			return getAllFiles(file).stream().filter(x -> x.isFile())
					.map(x -> x.getAbsolutePath()).filter(x -> x.endsWith(".class"))
					.filter(x -> !x.endsWith("-info.class"))
					.map(x ->
					{
						var len = x.length();
						return x.substring(0, len - 6).substring(length);
					}).map(x -> x.replace('\\', '.').replace('/', '.'));
		}catch(RuntimeException ignore) {
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Collection<File> getAllFiles(File file) {
		var result = new ArrayList<File>();
		getAllFiles(file, result);
		return result;
	}
	
	private void getAllFiles(File file, Collection<? super File> result) {
		result.add(file);
		if(file.isDirectory())
			for(var f : file.listFiles())
				getAllFiles(f, result);
	}
	
}
