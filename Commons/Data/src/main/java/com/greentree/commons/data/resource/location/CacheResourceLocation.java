package com.greentree.commons.data.resource.location;

import com.greentree.commons.data.resource.IOResource;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.ResourceAction;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CacheResourceLocation implements IOResourceLocation {

    private static final long serialVersionUID = 1L;

    private final ResourceLocation sourceLocation;

    private final IOResourceLocation cacheLocation;

    public CacheResourceLocation(ResourceLocation sourceLocation,
                                 IOResourceLocation cacheLocation) {
        this.sourceLocation = sourceLocation;
        this.cacheLocation = cacheLocation;
    }

    @Override
    public void clear() {
        cacheLocation.clear();
    }

    @Override
    public synchronized IOResource getResource(String name) {
        final var cacheName = name;
        if (cacheLocation.isExist(cacheName)) {
            if (sourceLocation.isExist(name)) {
                final var cache = cacheLocation.getResource(cacheName);
                final var source = sourceLocation.getResource(name);
                cacheLocation.deleteResource(cacheName);
                source.writeTo(cache, cache.lastModified());
                return new CacheResource(source, cache);
            } else {
                cacheLocation.deleteResource(cacheName);
                return null;
            }
        } else if (sourceLocation.isExist(name)) {
            final var cache = cacheLocation.createResource(cacheName);
            final var source = sourceLocation.getResource(name);
            source.writeTo(cache);
            return new CacheResource(source, cache);
        } else
            return null;
    }

    @Override
    public IOResource createNewResource(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteResource(String name) {
        return cacheLocation.deleteResource(name);
    }

    public static final class CacheResource implements IOResource {

        private static final long serialVersionUID = 1L;
        private final Resource source;
        private final IOResource cache;

        public CacheResource(Resource source, IOResource cache) {
            this.source = source;
            this.cache = cache;
        }

        @Override
        public ResourceAction getAction() {
            return source.getAction();
        }

        @Override
        public String getName() {
            return cache.getName();
        }

        @Override
        public long length() {
            return cache.length();
        }

        @Override
        public long lastModified() {
            return cache.lastModified();
        }

        @Override
        public InputStream open() {
            return cache.open();
        }

        @Override
        public URL url() throws MalformedURLException {
            return cache.url();
        }

        @Override
        public boolean delete() {
            return cache.delete();
        }

        @Override
        public boolean exists() {
            return cache.exists();
        }

        @Override
        public OutputStream openWrite() {
            throw new UnsupportedOperationException();
        }

    }

}
