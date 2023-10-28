package com.greentree.commons.math.matrix

import java.io.Serializable

interface AbstractMatrix<T> : Serializable, Iterable<Iterable<T>> {
}