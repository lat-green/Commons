package test.com.greentree.commons.reflection

import com.greentree.commons.reflection.ParameterizedTypeImpl
import com.greentree.commons.reflection.ParameterizedTypeUtil
import com.greentree.commons.reflection.info.ParameterizedTypeInfo
import com.greentree.commons.reflection.info.TypeInfo
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.jvm.javaType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParameterizedTypeUtilTest {

	@Test
	fun isExtends() {
		assertIsExtendsTrue(String::class.java, String::class.java)
		assertIsExtendsTrue(
			ParameterizedTypeImpl(Iterable::class.java, String::class.java),
			ParameterizedTypeImpl(Collection::class.java, String::class.java),
		)
		assertIsExtendsTrue(
			ParameterizedTypeImpl(Iterable::class.java, Any::class.java),
			ParameterizedTypeImpl(Collection::class.java, String::class.java),
		)
		assertIsExtendsFalse(
			ParameterizedTypeImpl(Iterable::class.java, Integer::class.java),
			ParameterizedTypeImpl(Collection::class.java, String::class.java),
		)
		assertIsExtendsFalse(
			ParameterizedTypeImpl(Collection::class.java, String::class.java),
			ParameterizedTypeImpl(Iterable::class.java, String::class.java),
		)
		assertIsExtendsTrue(
			ParameterizedTypeImpl(MutableCollection::class.java, String::class.java),
			ParameterizedTypeUtilTest::a.returnType.javaType,
		)
		assertIsExtendsTrue(
			Map::class.java,
			ParameterizedTypeInfo.fromClass<LinkedHashMap<String, Int>>(
				String::class,
				Integer::class,
			),
		)
		assertIsExtendsTrue(
			TypeInfo(A::class.java),
			ParameterizedTypeInfo.fromClass<A<String>>(String::class),
		)
	}

	@Test
	fun getBaseOrNull() {
		assertEquals(ParameterizedTypeUtil.getBaseOrNull(String::class, String::class), String::class.java)
		assertParameterizedTypeEquals(
			ParameterizedTypeUtil.getBaseOrNull(
				ParameterizedTypeUtilTest::a.returnType.javaType,
				MutableCollection::class
			),
			ParameterizedTypeUtilTest::b.returnType.javaType,
		)
	}

	companion object {

		val a: ArrayList<in String>? = null
		val b: MutableCollection<in String>? = null
	}
}

interface A<out T : Any>

private fun assertIsExtendsFalse(
	superType: Type,
	type: Type,
) {
	return assertFalse(ParameterizedTypeUtil.isExtends(superType, type), "superType: $superType, type: $type")
}

private fun assertIsExtendsTrue(
	superType: Type,
	type: Type,
) {
	return assertTrue(ParameterizedTypeUtil.isExtends(superType, type), "superType: $superType, type: $type")
}

private fun <T> assertParameterizedTypeEquals(expected: T, actual: T) = assertParameterizedTypeEquals(
	expected as ParameterizedType,
	actual as ParameterizedType,
)

@JvmName("assertParameterizedTypeEquals_ParameterizedType")
private fun <T : ParameterizedType> assertParameterizedTypeEquals(expected: T, actual: T) {
	assertEquals(expected.rawType, actual.rawType)
}