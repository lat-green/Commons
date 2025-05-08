package com.greentree.commons.math

import org.joml.Matrix3f
import org.joml.Matrix3fc

fun Matrix3fc.copy() = Matrix3f(this)