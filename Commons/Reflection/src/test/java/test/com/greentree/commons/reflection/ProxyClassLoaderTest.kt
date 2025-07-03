package test.com.greentree.commons.reflection

import com.greentree.commons.reflection.MyInvocationHandler
import com.greentree.commons.reflection.ProxyClassLoader
import kotlin.test.Test

class ProxyClassLoaderTest {

	interface A {

		fun foo()
	}

	@Test
	fun test1() {
		val classLoader = ProxyClassLoader()
		val a = classLoader.newProxyInstance(A::class.java, object : MyInvocationHandler {
			override fun invoke(thisRef: Any, methodName: String): Any? {
				println(methodName)
				return Unit
			}
		})
		a.foo()
	}
}