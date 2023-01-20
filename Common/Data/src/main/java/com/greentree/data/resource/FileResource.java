package com.greentree.data.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.greentree.common.util.exception.WrappedException;
import com.greentree.data.FileWatcher;

public final class FileResource implements IOResource {
	
	private static final long serialVersionUID = 1L;
	
	private final File file;
	
	public FileResource(File file) {
		this.file = file;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		var other = (FileResource) obj;
		return Objects.equals(file, other.file);
	}
	
	@Override
	public boolean exists() {
		return file.exists();
	}
	
	@Override
	public ResourceAction getAction() {
		return FileWatcher.getFileAction(file);
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(file);
	}
	
	@Override
	public long lastModified() {
		return file.lastModified();
	}
	
	@Override
	public long length() {
		return file.length();
	}
	
	@Override
	public InputStream open() {
		try {
			return new FileInputStream(file);
		}catch(FileNotFoundException e) {
			throw new WrappedException(e);
		}
	}
	
	@Override
	public OutputStream openWrite() {
		try {
			return new FileOutputStream(file);
		}catch(FileNotFoundException e) {
			throw new WrappedException(e);
		}
	}
	
	@Override
	public String toString() {
		return "FileResource [" + file + "]";
	}
	
	@Override
	public boolean delete() {
		return file.delete();
	}
	
}
