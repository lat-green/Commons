package com.greentree.commons.data.resource;

import com.greentree.commons.data.FileWatcher;
import com.greentree.commons.util.exception.WrappedException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public final class FileResource implements IOResource {

    private static final long serialVersionUID = 1L;

    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public ResourceAction getAction() {
        return FileWatcher.getFileAction(file);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public long length() {
        return file.length();
    }

    @Override
    public long lastModified() {
        return file.lastModified();
    }

    @Override
    public InputStream open() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new WrappedException(e);
        }
    }

    @Override
    public URL url() throws MalformedURLException {
        return file.toURI().toURL();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    public File getFile() {
        return file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        var other = (FileResource) obj;
        return Objects.equals(file, other.file);
    }

    @Override
    public String toString() {
        return "FileResource [" + file + "]";
    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public OutputStream openWrite() {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new WrappedException(e);
        }
    }

}
