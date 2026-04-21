package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.InMemoryFileResource

/**
 * Псевдоним для InMemoryResourceLocation - типа на основе MapResourceLocation
 * с InMemoryFileResource в качестве типа ресурсов.
 *
 * @see MapResourceLocation
 * @see InMemoryFileResource
 */
typealias InMemoryResourceLocation = MapResourceLocation<InMemoryFileResource>
