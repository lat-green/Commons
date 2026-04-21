package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.NotFoundResource
import com.greentree.commons.data.resource.Resource

/**
 * Реализация ResourceLocation на основе Map/Iterable ресурсов.
 * Позволяет создавать местоположение ресурса из коллекции ресурсов.
 * 
 * @param R тип ресурса
 * @property resources коллекция ресурсов
 * @see ResourceLocation
 * @see NotFoundResource
 */
data class MapResourceLocation<R : Resource>(
	val resources: Iterable<R>,
) : ResourceLocation {

	/**
	 * Вторичный конструктор с vararg параметрами.
	 * @param resources ресурсы
	 */
	constructor(vararg resources: R) : this(listOf(*resources))

	/**
	 * Возвращает ресурс по имени или NotFoundResource, если не найден.
	 * @param name имя ресурса
	 * @return Resource
	 */
	override fun getResource(name: String): Resource =
		resources.firstOrNull { it.name == name } ?: NotFoundResource(name)
}