package com.greentree.commons.math

import org.joml.Matrix4f
import org.joml.Matrix4fc

fun Matrix4fc.copy() = Matrix4f(this)