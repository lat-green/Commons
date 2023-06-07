package com.greentree.commons.data.resource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.locks.StampedLock;

public final class InMemoryResource implements IOResource {

    private static final long serialVersionUID = 1L;
    private final ResourceActionImpl action = new ResourceActionImpl();
    private final String name;
    private final StampedLock lock = new StampedLock();
    private long lastModified;
    private byte[] data;

    public InMemoryResource(String name, byte[] data) {
        this(name);
        setData(data);
    }

    public InMemoryResource(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        final var clone = data.clone();
        final long stamp = lock.writeLock();
        try {
            setData0(clone);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    private void setData0(byte[] data) {
        if (exists())
            action.eventModify();
        else
            action.eventCreate();
        this.data = data.clone();
        setLastModified();
    }

    private void setLastModified() {
        lastModified = System.currentTimeMillis();
    }

    @Override
    public ResourceAction getAction() {
        return action;
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
        final var in = new ByteArrayInputStream(data) {

            private final long stamp = lock.readLock();

            @Override
            public void close() throws IOException {
                super.close();
                lock.unlockWrite(stamp);
            }

        };
        return in;
    }

    @Override
    public URL url() throws MalformedURLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete() {
        if (!exists())
            return false;
        action.eventDelete();
        data = null;
        return true;
    }

    @Override
    public boolean exists() {
        return data != null;
    }

    @Override
    public OutputStream openWrite() {
        final var out = new ByteArrayOutputStream() {

            private final long stamp = lock.writeLock();

            @Override
            public void close() throws IOException {
                setData0(toByteArray());
                super.close();
                lock.unlockWrite(stamp);
            }
        };
        return out;
    }

    public void setData(byte[] data, int from, int to) {
        final var clone = Arrays.copyOfRange(data, from, to);
        final long stamp = lock.writeLock();
        try {
            setData0(clone);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

}
