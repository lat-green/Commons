package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.serialization.context.AnnotatedElementProperty
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.serializator.filter.ContextSerializatorFilter

data object AccuracySerializatorFilter : ContextSerializatorFilter {

	override fun filter(context: SerializationContext): SerializationContext {
		var ctx = context
		context.getPropertyOrNull(AnnotatedElementProperty)?.let { annotatedElement ->
			annotatedElement.getAnnotation(DoubleAccuracy::class.java)?.let { accuracy ->
				ctx += DoubleAccuracy.Property(accuracy)
			}
			annotatedElement.getAnnotation(FloatAccuracy::class.java)?.let { accuracy ->
				ctx += FloatAccuracy.Property(accuracy)
			}
			annotatedElement.getAnnotation(IntAccuracy::class.java)?.let { accuracy ->
				ctx += IntAccuracy.Property(accuracy)
			}
			annotatedElement.getAnnotation(LongAccuracy::class.java)?.let { accuracy ->
				ctx += LongAccuracy.Property(accuracy)
			}
			annotatedElement.getAnnotation(ShortAccuracy::class.java)?.let { accuracy ->
				ctx += ShortAccuracy.Property(accuracy)
			}
		}
		return ctx
	}
}