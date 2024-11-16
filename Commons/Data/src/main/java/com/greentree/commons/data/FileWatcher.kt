package com.greentree.commons.data

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observable.RunObservable
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.io.path.exists

data object FileWatcher {

	private val watchService: WatchService = FileSystems.getDefault().newWatchService()
	private val keys: MutableMap<WatchKey, Path> = WeakHashMap()
	private val watchers = mutableListOf<FileEventWatcher>()

	fun onFile(file: File, vararg events: WatchEvent.Kind<*>): RunObservable {
		require(!file.exists() || file.isFile)
		return OnKindFileWatcher(
			file.toPath(),
			*events
		)
	}

	fun onFile(file: Path, vararg events: WatchEvent.Kind<*>): RunObservable {
		require(!file.exists() || file.toFile().isFile)
		return OnKindFileWatcher(
			file,
			*events
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
		vararg val kind: WatchEvent.Kind<*>,
	) : RunObservable {

		override fun addListener(listener: Runnable): ListenerCloser {
			val key = registerDirectory(file.parent, *kind)
			val watcher = object : FileEventWatcher {
				override fun onEvent() {
					for(event in key.pollEvents()) {
						val path = file.parent.resolve(event.context() as Path)
						if(path == file)
							listener.run()
					}
					key.reset()
				}
			}
			watchers.add(watcher)
			return ListenerCloser {
				watchers.remove(watcher)
				key.cancel()
			}
		}
	}

	fun take() {
		val key = watchService.take()
		resolveKey(key)
	}

	fun poll() {
		val key = watchService.poll()
		if(key != null)
			resolveKey(key)
	}

	fun poll(timeout: Long, unit: TimeUnit) {
		val key = watchService.poll(timeout, unit)
		if(key != null)
			resolveKey(key)
	}

	private fun resolveKey(key: WatchKey) {
		for(watcher in watchers) {
			watcher.onEvent()
		}
	}

	private fun registerDirectory(directory: Path, vararg events: WatchEvent.Kind<*>): WatchKey {
		val key = directory.register(watchService, events)
		keys[key] = directory
		return key
	}
}

interface FileEventWatcher {

	fun onEvent()
}