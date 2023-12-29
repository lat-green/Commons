package com.greentree.commons.serialization.data

interface DecodeDataFormat<T> {

	fun decoder(element: T): Decoder
}