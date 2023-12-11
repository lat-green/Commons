package com.greentree.commons.action.event.bus

class EventBusImpl<K, A> : EventBus<K, A> {

	private val topics = mutableMapOf<K, TopicImpl<A>>()

	private class TopicImpl<T> : EventBus.Topic<T> {

		private val listeners = mutableListOf<(T) -> Unit>()

		override fun addListener(listener: (T) -> Unit) {
			listeners.add(listener)
		}

		override fun removeListener(listener: (T) -> Unit) {
			listeners.remove(listener)
		}

		override fun event(argument: T) {
			for(listener in listeners)
				listener(argument)
		}
	}

	override fun topic(key: K): EventBus.Topic<A> = topics.getOrPut(key) { TopicImpl() }
}