package com.greentree.commons.data.resource

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path

/**
 * Фабрика для создания ресурсов различных типов.
 * Предоставляет статические методы для создания файловых ресурсов
 * из различных источников (File, Path, строка, Class).
 * 
 * @see SystemFileResource
 * @see InMemoryFileResource
 * @see ClassRootResource
 */
object Resources {

	/**
	 * Создает ресурс из файла.
	 * @param file Java File объект
	 * @return SystemFileResource
	 */
	@JvmStatic
	fun of(file: File) = SystemFileResource(file)

	/**
	 * Создает ресурс из пути.
	 * @param path путь к файлу
	 * @return SystemFileResource
	 */
	@JvmStatic
	fun of(path: Path) = of(path.toFile())

	/**
	 * Создает ресурс в памяти с указанным текстом.
	 * @param name имя файла
	 * @param text содержимое файла
	 * @param charset кодировка (по умолчанию UTF-8)
	 * @return InMemoryFileResource
	 */
	@JvmStatic
	fun of(name: String, text: String, charset: Charset = Charsets.UTF_8) =
		InMemoryFileResource(name, text.toByteArray(charset))

	/**
	 * Создает ресурс из класса (для загрузки ресурсов из classpath).
	 * @param cls класс, чей ClassLoader будет использоваться
	 * @return ClassRootResource
	 */
	@JvmStatic
	fun of(cls: Class<*>) = ClassRootResource(cls)
}