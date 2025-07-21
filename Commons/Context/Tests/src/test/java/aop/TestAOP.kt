package aop

import com.greentree.engine.rex.fuse.tests.AutowiredTest
import com.greentree.engine.rex.fuse.tests.ContextTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

@ContextTest(TestConfig::class)
class TestAOP {

	@Nested
	internal inner class NestedAOP {

		@AutowiredTest
		fun shapeAreaMoreZero(shape: Shape) {
			Assertions.assertTrue(shape.area > 0f)
		}
	}

	@AutowiredTest
	fun notNull(person: Person) {
		Assertions.assertNotNull(person.name)
	}

	@AutowiredTest
	fun doubleArgumentNotNull(person: Person?, shape: Shape?) {
		Assertions.assertNotNull(person)
		Assertions.assertNotNull(person!!.name)
		Assertions.assertNotNull(shape)
	}

	@AutowiredTest
	fun shapeAreaMoreZero(shape: Shape) {
		Assertions.assertTrue(shape.area > 0f)
	}
}
