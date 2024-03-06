package com.greentree.commons.data.resource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public final class InMemoryResource implements IOResource {

    private static final long serialVersionUID = 1L;
    private final String name;
    private long lastModified;
    private byte[] data;

    public InMemoryResource(String name, byte[] data) {
        this(name);
        setData(data);
    }

    public InMemoryResource(String name) {
        this.name = name;
    }

    @Deprecated
    public void setData(byte[] data) {
        final var clone = data.clone();
        setData0(clone);
    }

    private void setData0(byte[] data) {
        this.data = data;
        setLastModified();
    }

    private void setLastModified() {
        lastModified = System.currentTimeMillis();
    }

    @Override
    public ResourceAction getAction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long length() {
        return data.length;
    }

    @Override
    public long lastModified() {
        return lastModified;
    }

    @Override
    public InputStream open() {
        return new ByteArrayInputStream(data);
    }

    @Override
    public URL url() throws MalformedURLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists() {
        return data != null;
    }

    @Override
    public boolean delete() {
        if (!exists())
            return false;
        data = null;
        return true;
    }

    @Override
    public OutputStream openWrite() {
        final var out = new ByteArrayOutputStream() {

            @Override
            public void close() throws IOException {
                setData0(toByteArray());
                super.close();
            }
        };
        return out;
    }

    @Deprecated
    public void setData(byte[] data, int from, int to) {
        final var clone = Arrays.copyOfRange(data, from, to);
        setData0(clone);
    }

}
