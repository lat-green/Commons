package com.greentree.commons.context.layer

import com.greentree.commons.context.BeanContext
import com.greentree.commons.graph.tree

fun interface BeanLayer {

	val dependencies: Sequence<BeanLayer>
		get() = sequenceOf()

	fun BeanContext.Builder.register()

	companion object {

		fun merge(vararg layers: BeanLayer) = merge(sequenceOf(*layers))
		fun merge(layers: Iterable<BeanLayer>) = merge(layers.asSequence())
		fun merge(layers: Sequence<BeanLayer>) = object : BeanLayer {
			override val dependencies: Sequence<BeanLayer>
				get() = layers

			override fun BeanContext.Builder.register() {
			}
		}
	}
}

fun BeanContext.Builder.registerLayer(layer: BeanLayer) = registerLayer(sequenceOf(layer))
fun BeanContext.Builder.registerLayer(vararg layers: BeanLayer) = registerLayer(sequenceOf(*layers))
fun BeanContext.Builder.registerLayer(layers: Iterable<BeanLayer>) = registerLayer(layers.asSequence())

fun BeanContext.Builder.registerLayer(layers: Sequence<BeanLayer>) {
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

