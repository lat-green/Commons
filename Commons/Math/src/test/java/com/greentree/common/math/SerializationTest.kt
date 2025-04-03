package com.greentree.common.math

import com.greentree.commons.math.serializator.MathSerialization
import com.greentree.commons.serialization.serializator.manager.SerializatorManager
import com.greentree.engine.rex.fuse.tests.ContextTest

@ContextTest(MathSerialization::class)
abstract class SerializationTest {

	protected lateinit var manager: SerializatorManager
}
