//
// Decompiled by Procyon v0.5.36
//
package com.greentree.commons.image.loader;

import java.io.IOException;
import java.io.InputStream;

import com.greentree.commons.image.image.ImageData;

public interface ImageDataLoader {

	ImageData loadImage(final InputStream p0) throws IOException;

}
