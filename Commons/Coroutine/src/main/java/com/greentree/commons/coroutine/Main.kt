package com.greentree.commons.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

//fun CoroutineScope.time() = produce {
//	while(isActive)
//		send(System.currentTimeMillis())
//}
//
//suspend inline fun delta(crossinline block: CoroutineScope.() -> Unit): Double = coroutineScope {
//	val time = time()
//	val t = time.receive()
//	block()
//	(time.receive() - t) / 1_000.0
//}
suspend inline fun <T, R> CoroutineScope.copyTo(
	origin: ReceiveChannel<T>,
	dest: SendChannel<R>,
	crossinline block: (T) -> R,
) {
	launch {
		for(v in origin)
			dest.send(block(v))
		dest.close()
	}
}

fun main(): Unit =
	runBlocking(Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()) {
		val a = Channel<String>()
		val b = Channel<String>()
		copyTo(a, b) { it }
		launch {
			for(x in b)
				println(x)
		}
		a.send("A")
		a.send("B")
		a.send("C")
		a.close()
	}
