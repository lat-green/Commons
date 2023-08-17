package com.greentree.commons.data.resource.location

import com.greentree.commons.data.FileUtil
import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.exception.WrappedException
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ZipResourceLocation(private val zip: Resource) : NamedResourceLocation, ResourceLocation {

	override val names: Iterable<String>
		get() = _names
	private val _names: MutableCollection<String> = ArrayList()

	init {
		zip.open().use { `in` ->
			ZipInputStream(`in`).use { zip_in ->
				var entry: ZipEntry
				while(zip_in.nextEntry.also { entry = it } != null) {
					val name = entry.name
					this._names.add(name)
				}
			}
		}
	}

	override fun getResource(name: String): Resource {
		zip.open().use { `in` ->
			ZipInputStream(`in`).use { zip_in ->
				val entry = found(name, zip_in)
				return when(entry) {
					null -> Resource.Null
					else -> ZipEntryResource(entry)
				}
			}
		}
	}

	private fun found(name: String, zip: ZipInputStream): ZipEntry? {
		var entry: ZipEntry
		while(zip.nextEntry.also { entry = it } != null) {
			val n = entry.name
			if(equals(name, n)) return entry
		}
		return null
	}

	private fun equals(name: String, entryName: String): Boolean {
		if(entryName[entryName.length - 1] == '/') return false // is folder
		val nf = FileUtil.getFileNameWithOutFolder(entryName)
		val n0 = FileUtil.getFileNameWithOutExtension(nf)
		return if(n0.isBlank()) if(nf.isBlank()) name == entryName else name == nf || name == entryName else name == n0 || name == nf || name == entryName
	}

	@Throws(IOException::class)
	private fun skipTo(entry: ZipEntry?, zip: ZipInputStream) {
		var e: ZipEntry
		while(zip.nextEntry.also { e = it } != null) if(entry!!.name == e.name) return
		throw IllegalArgumentException()
	}

	inner class ZipEntryResource(private val entry: ZipEntry) : Resource {

		override val name: String
			get() = zip.name + File.separator + entry.name

		override fun length() = entry.size

		override fun lastModified() = entry.lastModifiedTime.toMillis()

		override fun exists() = true

		override fun toString(): String {
			return "ZipEntryResource [$entry in $zip]"
		}

		override fun open(): InputStream {
			try {
				zip.open().use { `in` ->
					ZipInputStream(`in`).use { zip_in ->
						skipTo(entry, zip_in)
						val bytes = zip_in.readNBytes(entry.size.toInt())
						return ByteArrayInputStream(bytes)
					}
				}
			} catch(e: IOException) {
				throw WrappedException(e)
			}
		}

		override fun url(): URL {
			throw UnsupportedOperationException()
		}
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}