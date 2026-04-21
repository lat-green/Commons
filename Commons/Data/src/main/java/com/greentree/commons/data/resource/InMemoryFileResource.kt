package com.greentree.commons.data.resource

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Реализация файлового ресурса, хранимого в памяти.
 * Содержимое файла хранится в виде массива байтов в оперативной памяти.
 *
 * @property name имя файла
 * @property parentResource родительская директория (опционально)
 * @see MutableFileResource
 * @see InMemoryResource
 */
class InMemoryFileResource(
	override val name: String,
	private var parentResource: InMemoryFolderResource? = null,
) : MutableFileResource, InMemoryResource {

	private var array: ByteArray? = null

	/**
	 * Родительский ресурс (директория).
	 */
	override val parent: ParentResource
		get() = parentResource ?: throw RuntimeException("parent not found")

	constructor(
		name: String,
		array: ByteArray,
		parentResource: InMemoryFolderResource? = null,
	) : this(name, parentResource) {
		this.array = array
	}

	private var lastModified: Long = System.currentTimeMillis()

	/**
	 * Устанавливает время последней модификации файла.
	 * @param time время в миллисекундах с начала эпохи
	 */
	override fun setLastModified(time: Long) {
		lastModified = time
	}

	/**
	 * Создает пустой файл, если он не существует.
	 * @return true, если файл был создан; false, если файл уже существует
	 */
	override fun createThisFile(): Boolean {
		if(exists())
			return false
		array = ByteArray(0)
		lastModified = System.currentTimeMillis()
		return true
	}

	/**
	 * Открывает поток вывода для записи в файл.
	 * При закрытии потока данные записываются в массив.
	 * @return OutputStream для записи
	 */
	override fun openWrite() = object : ByteArrayOutputStream() {
		override fun close() {
			super.close()
			array = toByteArray()
			lastModified = System.currentTimeMillis()
		}
	}

	/**
	 * Размер файла в байтах.
	 * @return размер файла или 0, если файл не существует
	 */
	override val length: Long
		get() = checkExists().size.toLong()

	/**
	 * Открывает поток ввода для чтения из файла.
	 * @return InputStream для чтения
	 */
	override fun open() = ByteArrayInputStream(checkExists())

	/**
	 * Проверяет существование файла и возвращает его содержимое.
	 * @return массив байтов файла
	 * @throws ResourceNotFound если файл не существует
	 */
	private fun checkExists() = array ?: throw ResourceNotFound(name)

	/**
	 * Проверяет, существует ли файл.
	 * @return true, если файл существует (содержимое не пустое)
	 */
	override fun exists() = array != null

	/**
	 * Возвращает время последней модификации файла.
	 * @return время в миллисекундах с начала эпохи
	 */
	override fun lastModified() = lastModified

	/**
	 * Удаляет файл.
	 * @return true, если файл был удален
	 */
	override fun delete(): Boolean {
		if(array == null)
			return false
		array = null
		parentResource?.removeChild(this)
		parentResource = null
		lastModified = System.currentTimeMillis()
		return true
	}

	override fun toString(): String {
		val array = array
		return if(array != null)
			"InMemoryFileResource(name='$name', array=[${array.joinToString(limit = 10)}])"
		else
			"InMemoryFileResource(name='$name')"
	}

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as InMemoryFileResource

		if(name != other.name) return false
		if(parentResource != other.parentResource) return false

		return true
	}

	override fun hashCode(): Int {
		var result = name.hashCode()
		result = 31 * result + (parentResource?.hashCode() ?: 0)
		return result
	}
}