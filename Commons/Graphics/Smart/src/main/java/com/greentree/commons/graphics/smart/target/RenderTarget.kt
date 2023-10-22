package com.greentree.commons.graphics.smart.target

import com.greentree.commons.image.PixelFormat

interface RenderTarget {

	fun buffer(): RenderCommandBuffer

}
