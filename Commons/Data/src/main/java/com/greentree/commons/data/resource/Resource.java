package com.greentree.commons.data.resource;

import com.greentree.commons.util.exception.WrappedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public interface Resource extends Serializable {

    /** @return Action or {@link NullResourceAction#NULL_RESOURCE_ACTION} */
    ResourceAction getAction();

    String getName();

    /** @return length or -1 if unknown */
    long length();

    default void writeTo(IOResource result, long lastRead) {
        final var m = lastModified();
        if (m == 0 || m > lastRead)
            writeTo(result);
    }

    long lastModified();

    default void writeTo(IOResource result) {
        try (final var out = result.openWrite(); final var in = open()) {
            in.transferTo(out);
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    InputStream open();

    URL url() throws MalformedURLException;

}
