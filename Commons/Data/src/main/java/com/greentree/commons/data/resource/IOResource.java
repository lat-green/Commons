package com.greentree.commons.data.resource;

import java.io.OutputStream;

public interface IOResource extends Resource {

    /**
     * @return {@code true} if and only if the file or directory is successfully
     * deleted; {@code false} otherwise
     */
    boolean delete();

    OutputStream openWrite();

}
