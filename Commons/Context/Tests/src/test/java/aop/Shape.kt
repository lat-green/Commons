package aop

sealed interface Shape {

	val area: Float
}

data class Triangle(override val area: Float) : Shape
data class Rect(override val area: Float) : Shape
