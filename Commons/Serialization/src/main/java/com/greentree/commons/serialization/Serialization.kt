package com.greentree.commons.serialization

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.layer.BeanLayer
import com.greentree.commons.context.layer.ContextBeanLayer
import com.greentree.commons.context.registerInstance
import com.greentree.commons.context.registerSingleton
import com.greentree.commons.context.resolveBean
import com.greentree.commons.serialization.context.BeanContextProperty
import com.greentree.commons.serialization.serializator.BitSetSerializator
import com.greentree.commons.serialization.serializator.BooleanSerializator
import com.greentree.commons.serialization.serializator.ByteSerializator
import com.greentree.commons.serialization.serializator.ClassSerializator
import com.greentree.commons.serialization.serializator.DoubleSerializator
import com.greentree.commons.serialization.serializator.EnumAsByteSerializator
import com.greentree.commons.serialization.serializator.FloatSerializator
import com.greentree.commons.serialization.serializator.IntArraySerializator
import com.greentree.commons.serialization.serializator.IntSerializator
import com.greentree.commons.serialization.serializator.LongSerializator
import com.greentree.commons.serialization.serializator.ShortSerializator
import com.greentree.commons.serialization.serializator.StringSerializator
import com.greentree.commons.serialization.serializator.accuracy.AccuracySerializatorFilter
import com.greentree.commons.serialization.serializator.filter.AddSerializationContextSerializatorFilter
import com.greentree.commons.serialization.serializator.filter.ExceptionSerializatorFilter
import com.greentree.commons.serialization.serializator.manager.SerializatorManagerImpl
import com.greentree.commons.serialization.serializator.provider.ArraySerializator
import com.greentree.commons.serialization.serializator.provider.DataClassSerializator
import com.greentree.commons.serialization.serializator.provider.ObjectSerializatorProvider
import com.greentree.commons.serialization.serializator.provider.UnsafeRealSerializator
import com.greentree.commons.serialization.serializator.type.ClassAsNameSerializator
import com.greentree.commons.serialization.serializator.type.KotlinObjectTypeSerializator
import com.greentree.commons.serialization.serializator.type.KotlinSealedTypeSerializator
import com.greentree.engine.rex.serialization.serializator.provider.KotlinObjectSerializator

data object Serialization : BeanLayer {

	override val dependencies
		get() = sequenceOf(ContextBeanLayer)

	override fun MutableBeanContext.register() {
		registerSingleton("beanContextSerializatorFilter") {
			AddSerializationContextSerializatorFilter(BeanContextProperty(resolveBean()))
		}
		/* Serializators */
		registerInstance(BitSetSerializator)
		registerInstance(BooleanSerializator)
		registerInstance(ByteSerializator)
		registerSingleton(ClassSerializator::class)
		registerInstance(DoubleSerializator)
		registerInstance(FloatSerializator)
		registerInstance(IntArraySerializator)
		registerInstance(IntSerializator)
		registerInstance(LongSerializator)
		registerInstance(ShortSerializator)
		registerInstance(StringSerializator)
		/* SerializatorProviders */
		registerInstance(ArraySerializator)
		registerInstance(DataClassSerializator)
		registerInstance(KotlinObjectSerializator)
		registerInstance(ObjectSerializatorProvider)
		registerInstance(EnumAsByteSerializator)
		registerInstance(UnsafeRealSerializator)
		/* Filter */
		registerInstance(ExceptionSerializatorFilter)
		registerInstance(AccuracySerializatorFilter)
		/* TypeSerializator */
		registerInstance(ClassAsNameSerializator)
		registerInstance(KotlinObjectTypeSerializator)
		registerInstance(KotlinSealedTypeSerializator)
		/* Manager */
		registerSingleton(SerializatorManagerImpl::class)
	}
}
