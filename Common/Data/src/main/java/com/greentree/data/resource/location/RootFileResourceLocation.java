package com.greentree.data.resource.location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import com.greentree.common.util.iterator.IteratorUtil;
import com.greentree.data.FileUtil;
import com.greentree.data.resource.FileResource;
import com.greentree.data.resource.IOResource;

public class RootFileResourceLocation implements NamedResourceLocation, IOResourceLocation {
	
	private static final long serialVersionUID = 1L;
	private final File root;
	
	public RootFileResourceLocation(final File root) {
		Objects.requireNonNull(root);
		if(!root.exists())
			throw new IllegalArgumentException("root must by exists [root=" + root + "]");
		if(!root.isDirectory())
			throw new IllegalArgumentException("root must by directory [root=" + root + "]");
		this.root = root;
	}
	
	public RootFileResourceLocation(final Path path) {
		this(path.toFile());
	}
	
	public RootFileResourceLocation(final String file) {
		this(new File(file));
	}
	
	@Override
	public void clear() {
		FileUtil.clearDir(root);
	}
	
	@Override
	public IOResource createResource(String name) {
		final var f = new File(root, name);
		if(!f.exists())
			try {
				f.createNewFile();
			}catch(IOException e) {
				e.printStackTrace();
			}
		else
			checkFile(f);
		return new FileResource(f);
	}
	
	@Override
	public IOResource createNewResource(String name) {
		final var f = new File(root, name);
		checkFile(f);
		if(f.exists())
			throw new RuntimeException(f + "");
		return new FileResource(f);
	}
	
	private void checkFile(File file) {
		if(!file.isFile())
			throw new RuntimeException(file + " is not file");
	}
	
	@Override
	public Iterable<String> getAllNames() {
		return IteratorUtil.iterable(root.list());
	}
	
	@Override
	public IOResource getResource(String name) {
		final var f = new File(root, name);
		if(!f.exists())
			return null;
		checkFile(f);
		return new FileResource(f);
	}
	
	public File getRoot() {
		return root;
	}
	
	@Override
	public String toString() {
		return "FileSystemLocation[" + root + "]";
	}
	
	@Override
	public boolean deleteResource(String name) {
		final var f = new File(root, name);
		checkFile(f);
		
		if(!f.exists())
			return false;
		
		return f.delete();
	}
	
}
