package com.greentree.commons.util.react

fun <T> ReactContext.useRef() = useRef<T?>(null)
fun <T> ReactContext.useRef(onClose: (T?) -> Unit) = useRef<T?>(null, onClose)
fun <T> ReactContext.useRef(initialValue: T) = useRef(initialValue) {}
