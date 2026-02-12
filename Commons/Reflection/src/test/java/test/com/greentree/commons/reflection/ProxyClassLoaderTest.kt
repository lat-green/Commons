package test.com.greentree.commons.reflection

import com.greentree.commons.reflection.MyInvocationHandler
import com.greentree.commons.reflection.ProxyClassLoader
import kotlin.test.Test
import kotlin.test.assertEquals

class ProxyClassLoaderTest {

	interface A {

		fun foo()
	}

	@Test
	fun test1() {
		val classLoader = ProxyClassLoader()
		val methodNames = mutableListOf<String>()
		val a = classLoader.newProxyInstance(A::class.java, object : MyInvocationHandler {
			override fun invoke(thisRef: Any, methodName: String): Any? {
				methodNames.add(methodName)
				return Unit
			}
		})
		a.foo()
		assertEquals(listOf("foo"), methodNames)
	}
}