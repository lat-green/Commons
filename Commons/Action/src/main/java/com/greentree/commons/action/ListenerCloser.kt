package com.greentree.commons.action

fun interface ListenerCloser : AutoCloseable {

	override fun close()

	object Empty : ListenerCloser {

		override fun close() {
		}
	}

	companion object {

		fun merge(vararg closers: ListenerCloser): ListenerCloser {
			return ListenerCloser {
				for(c in closers)
					c.close()
			}
		}

		fun merge(closers: Iterable<ListenerCloser>): ListenerCloser {
			return ListenerCloser {
				for(c in closers)
					c.close()
			}
		}
	}
}
