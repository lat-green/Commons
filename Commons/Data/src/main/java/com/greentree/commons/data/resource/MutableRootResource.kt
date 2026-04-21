package com.greentree.commons.data.resource

/**
 * Интерфейс корневого ресурса, который можно изменять.
 * Представляет корневую директорию с возможностью записи.
 *
 * @see RootResource
 * @see MutableParentResource
 * @see SystemFileResource
 */
interface MutableRootResource : RootResource, MutableParentResource