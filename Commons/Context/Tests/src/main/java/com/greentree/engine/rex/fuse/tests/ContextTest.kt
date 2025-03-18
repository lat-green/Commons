package com.greentree.engine.rex.fuse.tests

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.context.layer.BeanLayer
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Inherited
@AnnotationInherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@ExtendWith(ContextExtension::class)
annotation class ContextTest(vararg val layers: KClass<out BeanLayer>)
