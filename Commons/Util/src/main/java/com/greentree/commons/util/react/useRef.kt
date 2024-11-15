package com.greentree.commons.util.react

fun <T> ReactContext.useRef() = useRef<T?>(null)
fun <T> ReactContext.useRef(onClose: (T?) -> Unit) = useRef<T?>(null, onClose)
fun <T> ReactContext.useRef(initialValue: T) = useRef(initialValue) {}

fun <T> useRef() = REACT.get().useRef<T>()
fun <T> useRef(onClose: (T?) -> Unit) = REACT.get().useRef(onClose)
fun <T> useRef(initialValue: T) = REACT.get().useRef(initialValue)
fun <T> useRef(initialValue: T, onClose: (T?) -> Unit) = REACT.get().useRef(initialValue, onClose)
