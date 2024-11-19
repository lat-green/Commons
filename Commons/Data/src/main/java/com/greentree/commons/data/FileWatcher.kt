package com.greentree.commons.data

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.container.MultiContainer
import com.greentree.commons.action.observable.RunObservable
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolute
import kotlin.io.path.exists

interface FileKeyWatcher {

	fun take()

	fun poll(): Boolean
	fun poll(timeout: Long, unit: TimeUnit): Boolean
}

data object FileWatcher : FileKeyWatcher {

	private val watchers = MultiContainer<FileKeyWatcher>()

	private fun onFile(file: File, vararg events: WatchEvent.Kind<*>) = onFile(file.toPath(), *events)

	private fun onFile(file: Path, vararg events: WatchEvent.Kind<*>): RunObservable {
		require(!file.exists() || file.toFile().isFile)
		return OnKindFileWatcher(
			file.absolute(),
			arrayOf(*events)
		)
	}

	fun onFileModify(file: File) = onFile(file, ENTRY_MODIFY)
	fun onFileModify(file: Path) = onFile(file, ENTRY_MODIFY)

	fun onFileCreate(file: File) = onFile(file, ENTRY_CREATE)
	fun onFileCreate(file: Path) = onFile(file, ENTRY_CREATE)

	fun onFileDelete(file: File) = onFile(file, ENTRY_DELETE)
	fun onFileDelete(file: Path) = onFile(file, ENTRY_DELETE)

	class OnKindFileWatcher(
		val file: Path,
		val events: Array<WatchEvent.Kind<*>>,
	) : RunObservable {

		val directory: Path
			get() = file.parent

		init {
			require(file.isAbsolute)
		}

		override fun addListener(listener: Runnable): ListenerCloser {
			val watchService: WatchService = FileSystems.getDefault().newWatchService()
			val thisKey = directory.register(watchService, events)
			val lc = watchers.add(object : FileKeyWatcher {
				override fun take() {
					val key = watchService.take()
					resolveKey(key)
				}

				override fun poll(): Boolean {
					val key = watchService.poll()
					if(key != null) {
						resolveKey(key)
						return true
					}
					return false
				}

				override fun poll(timeout: Long, unit: TimeUnit): Boolean {
					val key = watchService.poll(timeout, unit)
					if(key != null) {
						resolveKey(key)
						return true
					}
					return false
				}

				fun resolveKey(key: WatchKey) {
					for(event in key.pollEvents()) {
						if(event.kind() !in events)
							continue
						val path = directory.resolve(event.context() as Path)
						if(file == path)
							listener.run()
					}
					key.reset()
				}
			})
			return ListenerCloser {
				lc.close()
				thisKey.cancel()
				watchService.close()
			}
		}
	}

	override fun take() {
		while(!poll()) {
		}
	}

	override fun poll(): Boolean {
		var result = false
		for(watcher in watchers) {
			result = watcher.poll() || result
		}
		return result
	}

	override fun poll(timeout: Long, unit: TimeUnit): Boolean {
		val time = System.currentTimeMillis() + unit.toMillis(timeout)
		var result = false
		while(!result || time > System.currentTimeMillis()) {
			for(watcher in watchers) {
				result = watcher.poll(timeout, unit) || result
			}
		}
		return result
	}
}
