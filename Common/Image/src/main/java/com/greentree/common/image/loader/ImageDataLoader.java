//
// Decompiled by Procyon v0.5.36
//
package com.greentree.common.image.loader;

import java.io.IOException;
import java.io.InputStream;

import com.greentree.common.image.image.ImageData;

public interface ImageDataLoader {

	ImageData loadImage(final InputStream p0) throws IOException;

}
