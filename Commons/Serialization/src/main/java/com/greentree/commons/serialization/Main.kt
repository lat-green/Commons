package com.greentree.commons.serialization

import com.greentree.commons.serialization.serializer.NotFinalClassesSerializer
import com.greentree.commons.serialization.serializer.ReflectionSerializer
import java.lang.reflect.Modifier

data class Person(val name: Name, val age: UShort)

interface Name {

	val value: String
}

data class NameImpl(override val value: String) : Name
