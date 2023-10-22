//
// Decompiled by Procyon v0.5.36
//
package com.greentree.commons.image.loader;

import com.greentree.commons.image.image.ImageData;

import java.io.IOException;
import java.io.InputStream;

public interface ImageLoader {

    ImageData loadImage(InputStream in) throws IOException;

}
