package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.graph.tree

fun interface BeanLayer {

	val dependencies: Sequence<BeanLayer>
		get() = sequenceOf()

	fun MutableBeanContext.register()

	companion object {

		fun merge(vararg layers: BeanLayer) = merge(sequenceOf(*layers))
		fun merge(layers: Iterable<BeanLayer>) = merge(layers.asSequence())
		fun merge(layers: Sequence<BeanLayer>) = object : BeanLayer {
			override val dependencies: Sequence<BeanLayer>
				get() = layers

			override fun MutableBeanContext.register() {
			}
		}
	}
}

fun MutableBeanContext.registerLayer(layer: BeanLayer) = registerLayer(sequenceOf(layer))
fun MutableBeanContext.registerLayer(vararg layers: BeanLayer) = registerLayer(sequenceOf(*layers))
fun MutableBeanContext.registerLayer(layers: Iterable<BeanLayer>) = registerLayer(layers.asSequence())

fun MutableBeanContext.registerLayer(layers: Sequence<BeanLayer>) {
	val all = layers.flatMap { layer ->
		tree(layer) {
			yieldAll(it.dependencies)
		}
	}.distinct()
	for(layer in all) {
		layer.run {
			register()
		}
	}
}

