package com.greentree.commons.tests.aop

interface Name {

	val name: String
}

object Arseny : Name {

	override val name: String
		get() = "Arseny"
}

interface Person {

	val name: String
}

object Anton : Person {

	override val name: String
		get() = "Anton"
}

object Tom : Person {

	override val name: String
		get() = "Tom"
}

data class NamePerson(private val _name: Name) : Person {

	override val name: String
		get() = _name.name
}

