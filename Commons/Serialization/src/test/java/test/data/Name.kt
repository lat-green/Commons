package test.data

sealed interface Name {

	val value: String
}

data class NameImpl(override val value: String) : Name

data class ProxyName(val origin: Name) : Name by origin

data object Anton : Name {

	override val value: String
		get() = "Anton"
}