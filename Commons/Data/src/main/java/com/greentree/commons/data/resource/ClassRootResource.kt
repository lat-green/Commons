package com.greentree.commons.data.resource

/**
 * Реализация корневого ресурса на основе ClassLoader.
 * Позволяет загружать ресурсы из classpath с использованием ClassLoader.
 *
 * @property classLoader ClassLoader для загрузки ресурсов
 * @see MutableRootResource
 * @see Resources
 */
data class ClassRootResource(
	val classLoader: ClassLoader,
) : MutableRootResource {

	/**
	 * Вторичный конструктор, принимающий класс.
	 * @param cls класс, чей ClassLoader будет использоваться
	 */
	constructor(cls: Class<*>) : this(cls.classLoader)

	/**
	 * Получение списка дочерних ресурсов (не реализовано).
	 */
	override val children: Iterable<ChildResource>
		get() = TODO("Not yet implemented")

	/**
	 * Получение ресурса по имени (не реализовано).
	 * @param name имя ресурса
	 */
	override fun getChildren(name: String): ChildResource {
		TODO("Not yet implemented")
	}

	/**
	 * Получение имени ресурса (не реализовано).
	 */
	override val name: String
		get() = TODO("Not yet implemented")

	/**
	 * Получение времени последней модификации (не реализовано).
	 */
	override fun lastModified(): Long {
		TODO("Not yet implemented")
	}

	/**
	 * Установка времени последней модификации (не реализовано).
	 * @param time время модификации
	 */
	override fun setLastModified(time: Long) {
		TODO("Not yet implemented")
	}

	/**
	 * Удаление ресурса (не реализовано).
	 */
	override fun delete(): Boolean {
		TODO("Not yet implemented")
	}
}
