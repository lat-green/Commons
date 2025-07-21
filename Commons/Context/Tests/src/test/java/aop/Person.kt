package aop

interface Name {

	val name: String
}

data class TextName(override val name: String) : Name

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
	constructor(name: String) : this(TextName(name))

	override val name: String
		get() = _name.name
}

