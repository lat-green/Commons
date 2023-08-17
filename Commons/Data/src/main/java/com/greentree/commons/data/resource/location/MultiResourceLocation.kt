package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.exception.MultiException
import com.greentree.commons.util.iterator.IteratorUtil

class MultiResourceLocation private constructor(locations: Iterable<ResourceLocation> = IteratorUtil.empty()) :
	NamedResourceLocation, Iterable<ResourceLocation?> {

	private val locations: Iterable<ResourceLocation>

	init {
		this.locations = IteratorUtil.clone(locations)
	}

	fun builder(): Builder {
		return Builder(this)
	}

	override val names: Iterable<String>
		get() {
			val names: MutableCollection<Iterable<String>> = ArrayList()
			for(location in locations) if(location is NamedResourceLocation) names.add(location.names)
			return IteratorUtil.union(names)
		}

	override fun getResource(name: String): Resource {
		return getResourceLiniar(name)!!
	}

	override fun iterator(): Iterator<ResourceLocation> {
		return locations.iterator()
	}

	override fun toString(): String {
		return "ResourceLocationCollection [" + IteratorUtil.toString(locations) + "]"
	}

	private fun getResourceLiniar(name: String): Resource? {
		val exepts = ArrayList<Throwable>()
		for(loc in locations) try {
			return loc.getResource(name)
		} catch(e: Exception) {
			exepts.add(e)
		}
		if(exepts.isEmpty()) return null
		throw throwResourceNotFound(name, exepts)
	}

	private fun throwResourceNotFound(name: String, exepts: Collection<Throwable>): MultiException {
		return MultiException("Resource \"" + name + "\" not found in " + IteratorUtil.toString(locations), exepts)
	}

	class Builder(private val main: MultiResourceLocation) {

		private val list: MutableCollection<ResourceLocation> = HashSet()
		fun add(location: ResourceLocation): Builder {
			if(location is MultiResourceLocation) synchronized(list) { for(l in location) list.add(l) } else synchronized(
				list
			) { list.add(location) }
			return this
		}

		fun build(): MultiResourceLocation {
			synchronized(list) {
				return if(list.isEmpty()) main else MultiResourceLocation(list)
			}
		}
	}

	companion object {

		private const val serialVersionUID = 1L

		@JvmField
		val EMPTY = MultiResourceLocation()
	}
}