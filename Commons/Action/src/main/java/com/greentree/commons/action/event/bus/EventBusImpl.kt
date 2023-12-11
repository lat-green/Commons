package com.greentree.commons.action.event.bus

class EventBusImpl<K, L> : EventBus<K, L> {

	private val topics = mutableMapOf<K, TopicImpl<L>>()

	private class TopicImpl<L> : EventBus.Topic<L> {

		private val listeners = mutableListOf<L>()

		override fun addListener(listener: L) {
			listeners.add(listener)
		}

		override fun removeListener(listener: L) {
			listeners.remove(listener)
		}

		override fun event(block: (L) -> Unit) {
			for(listener in listeners)
				try {
					block(listener)
				} catch(e: Exception) {
					e.printStackTrace()
				}
		}
	}

	override fun topic(key: K): EventBus.Topic<L> = topics.getOrPut(key) { TopicImpl() }
}