package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.concurent.MultiTask
import java.io.File
import java.nio.file.Path
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

class RecursionFileSystemLocation(private val root: File) : NamedResourceLocation {

	private var locs: MultiResourceLocation? = null

	constructor(path: Path) : this(path.toFile())
	constructor(file: String?) : this(File(file))

	override val names: Iterable<String>
		get() = locations.names
	val locations: NamedResourceLocation
		get() {
			if(locs != null) return locs!!
			val builder: MultiResourceLocation.Builder = MultiResourceLocation.EMPTY.builder()
			val files = Stack<File>()
			val futures = Stack<Future<*>>()
			files.add(root)
			do {
				synchronized(files) {
					while(!files.isEmpty()) {
						val file = files.pop()
						futures.add(MultiTask.task {
							if(file.isDirectory) {
								builder.add(RootFileResourceLocation(file))
								synchronized(files) { Collections.addAll(files, *file.listFiles()) }
							}
						})
					}
				}
				for(f in futures) try {
					f.get()
				} catch(e: InterruptedException) {
					e.printStackTrace()
				} catch(e: ExecutionException) {
					e.printStackTrace()
				}
				futures.clear()
			} while(!files.isEmpty())
			locs = builder.build()
			return locs!!
		}

	override fun getResource(name: String): Resource {
		return locations.getResource(name)
	}

	override fun toString(): String {
		return "RecursionFileSystemLocation [$root]"
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}