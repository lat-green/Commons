package com.greentree.commons.data.resource

/**
 * Интерфейс родительского ресурса (директории), который можно изменять.
 * Предоставляет возможность управления дочерними ресурсами.
 * 
 * @see ParentResource
 * @see MutableResource
 * @see MutableFolderResource
 * @see MutableRootResource
 */
interface MutableParentResource : MutableResource, ParentResource