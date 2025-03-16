package com.greentree.commons.geometry.geom3d.face

import org.joml.Vector3fc

class FaceImpl(
	override val p1: Vector3fc,
	override val p2: Vector3fc,
	override val p3: Vector3fc,
) : Face
