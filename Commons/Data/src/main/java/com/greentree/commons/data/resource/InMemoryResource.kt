package com.greentree.commons.data.resource

/**
 * Запечатанный интерфейс для ресурсов, хранимых в памяти.
 * Представляет файлы и директории, которые существуют только в оперативной памяти
 * и не связаны с физической файловой системой.
 * 
 * @see InMemoryFileResource
 * @see InMemoryFolderResource
 * @see InMemoryProxyMutableResource
 */
sealed interface InMemoryResource : MutableChildResource


