package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource

/**
 * Интерфейс именованного местоположения ресурса.
 * Позволяет получать список всех имен ресурсов и преобразовывать
 * местоположение в Map.
 * 
 * @see ResourceLocation
 * @see MapResourceLocation
 */
interface NamedResourceLocation : ResourceLocation {

	/**
	 * Возвращает итератор по именам всех ресурсов.
	 */
	val names: Iterable<String>

	/**
	 * Преобразует местоположение в Map имя -> ресурс.
	 * @return Map имен ресурсов на сами ресурсы
	 */
	fun toMap(): Map<String, Resource> {
		val map = HashMap<String, Resource>()
		for(name in names)
			map[name] = getResource(name)
		return map
	}
}