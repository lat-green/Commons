package com.greentree.commons.data.resource.location;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.concurent.MultiTask;

public class RecursionFileSystemLocation implements NamedResourceLocation {

	private static final long serialVersionUID = 1L;
	private MultiResourceLocation locs;
	private final File root;

	public RecursionFileSystemLocation(final File root) {
		this.root = root;
	}

	public RecursionFileSystemLocation(final Path path) {
		this(path.toFile());
	}

	public RecursionFileSystemLocation(final String file) {
		this(new File(file));
	}

	@Override
	public Iterable<String> getAllNames() {
		return getLocations().getAllNames();
	}

	public NamedResourceLocation getLocations() {
		if(locs != null) return locs;
		final var builder = MultiResourceLocation.EMPTY.builder();

		final var files   = new Stack<File>();
		final var futures = new Stack<Future<?>>();
		files.add(root);
		do {
			synchronized(files) {
				while(!files.isEmpty()) {
					final var file = files.pop();
					futures.add(MultiTask.task(() -> {
						if(file.isDirectory()) {
							builder.add(new RootFileResourceLocation(file));
							synchronized(files) {
								Collections.addAll(files, file.listFiles());
							}
						}
					}));
				}
			}

			for(var f : futures)
				try {
					f.get();
				}catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			futures.clear();
		}while(!files.isEmpty());

		locs = builder.build();
		return locs;
	}

	@Override
	public Resource getResource(String name) {
		return getLocations().getResource(name);
	}

	@Override
	public String toString() {
		return "RecursionFileSystemLocation [" + root + "]";
	}

}
