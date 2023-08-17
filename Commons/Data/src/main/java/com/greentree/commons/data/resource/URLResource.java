package com.greentree.commons.data.resource;

import com.greentree.commons.util.exception.WrappedException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class URLResource implements Resource {

    private static final long serialVersionUID = 1L;

    private final URL url;

    public URLResource(URL url) {
        this.url = Objects.requireNonNull(url);
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        var other = (URLResource) obj;
        return Objects.equals(url, other.url);
    }

    @Override
    public String toString() {
        return "URLResource [" + url + "]";
    }

    @Override
    public ResourceAction getAction() {
        return NullResourceAction.NULL_RESOURCE_ACTION;
    }

    @Override
    public String getName() {
        return url.getFile();
    }

    @Override
    public long length() {
        try {
            final var connection = url.openConnection();
            if (connection instanceof HttpURLConnection http)
                http.setRequestMethod("HEAD");
            return connection.getContentLengthLong();
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    @Override
    public long lastModified() {
        try {
            final var connection = url.openConnection();
            if (connection instanceof HttpURLConnection http)
                http.setRequestMethod("HEAD");
            return connection.getLastModified();
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    @Override
    public InputStream open() {
        try {
            final var c = url.openConnection();
            c.connect();
            return c.getInputStream();
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    @Override
    public URL url() throws MalformedURLException {
        return url;
    }

    @Override
    public boolean exists() {
        return true;
    }

}
