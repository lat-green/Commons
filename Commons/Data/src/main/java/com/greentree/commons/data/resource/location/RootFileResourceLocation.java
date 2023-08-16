package com.greentree.commons.data.resource.location;

import com.greentree.commons.data.FileUtil;
import com.greentree.commons.data.resource.FileResource;
import com.greentree.commons.data.resource.IOResource;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class RootFileResourceLocation implements NamedResourceLocation, IOResourceLocation {

    private static final long serialVersionUID = 1L;
    private final File root;

    public RootFileResourceLocation(final Path path) {
        this(path.toFile());
    }

    public RootFileResourceLocation(final File root) {
        Objects.requireNonNull(root);
        if (!root.exists())
            throw new IllegalArgumentException("root must by exists [root=" + root + "]");
        if (!root.isDirectory())
            throw new IllegalArgumentException("root must by directory [root=" + root + "]");
        this.root = root;
    }

    public RootFileResourceLocation(final String file) {
        this(new File(file));
    }

    @Override
    public void clear() {
        FileUtil.clearDir(root);
    }

    @Override
    public IOResource getResource(String name) {
        Objects.requireNonNull(name);
        final var f = new File(root, name);
        if (!f.exists())
            return null;
        checkFile(f);
        return new FileResource(f);
    }

    @Override
    public boolean isExist(String name) {
        return new File(root, name).exists();
    }

    private void checkFile(File file) {
        if (!file.isFile())
            throw new RuntimeException(file + " is not file");
    }

    @Override
    public IOResource createNewResource(String name) {
        Objects.requireNonNull(name);
        final var f = new File(root, name);
        if (f.exists())
            throw new RuntimeException(f + "");
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("file:" + f, e);
        }
        return new FileResource(f);
    }

    @Override
    public boolean deleteResource(String name) {
        Objects.requireNonNull(name);
        final var f = new File(root, name);
        checkFile(f);
        if (!f.exists())
            return false;
        return f.delete();
    }

    @Override
    public IOResource createResource(String name) {
        Objects.requireNonNull(name);
        final var f = new File(root, name);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            checkFile(f);
        return new FileResource(f);
    }

    @Override
    public Iterable<String> getAllNames() {
        return IteratorUtil.iterable(root.list());
    }

    public File getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "FileSystemLocation[" + root + "]";
    }

}
