package com.greentree.common.graphics.sgl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.common.graphics.sgl.texture.builder.TextureBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2D;
import com.greentree.commons.image.ImageIODecoder;

public class BufferedImageUtil {
	
	public static GLTexture2D getTexture(final BufferedImage bufferedImage) throws IOException {
		GLPixelFormat srcPixelFormat;
		final var data = ImageIODecoder.toImageData(bufferedImage);
		
		if(bufferedImage.getColorModel().hasAlpha())
			srcPixelFormat = GLPixelFormat.RGBA;
		else
			srcPixelFormat = GLPixelFormat.RGB;
		
		var texture = TextureBuilder.builder(data.getData(), srcPixelFormat)
				.build2d(data.getWidth(), data.getHeight(), GLPixelFormat.RGBA);
		
		texture.bind();
		texture.setWrap(GLWrapping.CLAMP_TO_BORDER);
		texture.setFilter(GLFiltering.LINEAR);
		
		texture.unbind();
		
		return texture;
	}
}
