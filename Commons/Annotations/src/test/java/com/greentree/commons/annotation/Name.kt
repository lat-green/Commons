package com.greentree.commons.annotation

@Role("AAA")
annotation class Name(@get:AliasFor(value = "value", annotation = Role::class) val value: String)
