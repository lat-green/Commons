package com.greentree.commons.data.resource.location;

import java.io.File;
import java.nio.file.Path;

import com.greentree.commons.data.resource.FileResource;
import com.greentree.commons.data.resource.Resource;

public class RootFileSystemLocation implements ResourceLocation {

	private static final long serialVersionUID = 1L;
	
	private final File root;


	public RootFileSystemLocation(File root) {
		this.root = root;
	}
	public RootFileSystemLocation(Path path) {
		this(path.toFile());
	}
	public RootFileSystemLocation(String path) {
		this(new File(path));
	}

	@Override
	public Resource getResource(String name) {
		final var file = new File(root, name);
		if(!file.exists() || !file.isFile()) return null;
		return new FileResource(file);
	}

	public File getRoot() {
		return root;
	}

	@Override
	public String toString() {
		return "RootFileSystem [" + root + "]";
	}

}
