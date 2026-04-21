package com.greentree.commons.data.resource

/**
 * Интерфейс дочернего ресурса, который можно изменять.
 * Представляет собой файл или директорию внутри родительской директории
 * с возможностью записи.
 *
 * @see ChildResource
 * @see MutableResource
 * @see MutableFileResource
 * @see MutableFolderResource
 */
interface MutableChildResource : ChildResource, MutableResource