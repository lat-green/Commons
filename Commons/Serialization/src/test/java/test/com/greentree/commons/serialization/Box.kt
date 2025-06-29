package test.com.greentree.commons.serialization

open class TextBox(val text: String?) {

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as TextBox

		return text == other.text
	}

	override fun hashCode(): Int {
		return text.hashCode()
	}
}

fun Box(value: IntArray) = IntArrayBox(value)

data class IntArrayBox(val value: IntArray) {

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as IntArrayBox

		return value.contentEquals(other.value)
	}

	override fun hashCode(): Int {
		return value.contentHashCode()
	}

	override fun toString(): String {
		return value.contentToString()
	}
}

fun <T> Box(value: Array<T>) = ArrayOfTBox(value)

data class ArrayOfTBox<T>(val value: Array<T>) {

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ArrayOfTBox<T>

		return value.contentEquals(other.value)
	}

	override fun hashCode(): Int {
		return value.contentHashCode()
	}

	override fun toString(): String {
		return value.contentToString()
	}
}

fun Box(value: Array<Int>) = ArrayOfIntBox(value)

data class ArrayOfIntBox(val value: Array<Int>) {

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ArrayOfIntBox

		return value.contentEquals(other.value)
	}

	override fun hashCode(): Int {
		return value.contentHashCode()
	}

	override fun toString(): String {
		return value.contentToString()
	}
}

fun Box(value: Array<String>) = ArrayOfStringBox(value)

data class ArrayOfStringBox(val value: Array<String>) {

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ArrayOfStringBox

		return value.contentEquals(other.value)
	}

	override fun hashCode(): Int {
		return value.contentHashCode()
	}

	override fun toString(): String {
		return value.contentToString()
	}
}

fun Box(value: Any) = ObjectBox(value)

data class ObjectBox(
//	@RealType
	val value: Any,
) {

	init {
		require(value::class == Any::class) { "$value" }
	}

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ObjectBox

		return true
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}

	override fun toString(): String {
		return value.toString()
	}
}