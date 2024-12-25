package com.greentree.commons.action.event.bus

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observer.`object`.EventAction

class EventBusImpl<T> : EventBus<T> {

	private val topics = mutableListOf<TopicImpl<*, T>>()

	override fun event(event: T) {
		for(topic in topics) {
			topic.event(event)
		}
	}

	override fun <K> topic(keyExtractor: (T) -> K): EventBus.Topic<K, T> {
		return topics.firstOrNull { it.keyExtractor == keyExtractor } as EventBus.Topic<K, T>? ?: run {
			val topic = TopicImpl(keyExtractor)
			topics.add(topic)
			return topic
		}
	}

	class TopicImpl<K, T>(
		val keyExtractor: (T) -> K,
	) : EventBus.Topic<K, T> {

		private val listeners = mutableMapOf<K, EventAction<T>>()

		override fun addListener(key: K, listener: (T) -> Unit): ListenerCloser {
			return listeners.getOrPut(key) { EventAction() }.addListener(listener)
		}

		fun event(event: T) {
			val key = keyExtractor(event)
			listeners[key]?.event(event)
		}
	}
}