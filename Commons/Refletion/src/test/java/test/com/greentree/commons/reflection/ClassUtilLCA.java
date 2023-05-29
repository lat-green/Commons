package test.com.greentree.commons.reflection;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.greentree.commons.reflection.ClassUtil;

public class ClassUtilLCA {

	@Test
	void isExtends() {
		assertFalse(ClassUtil.isExtends(Float.class, Integer.class));
		assertFalse(ClassUtil.isExtends(Integer.class, Float.class));

		assertFalse(ClassUtil.isExtends(Integer.class, Object.class));
		assertTrue(ClassUtil.isExtends(Object.class, Integer.class));

	}

	@Test
	void lca() {

		var res = ClassUtil.lca(Arrays.asList(Float.class, Integer.class));

		assertTrue(ClassUtil.isExtends(res, Float.class));
		assertTrue(ClassUtil.isExtends(res, Integer.class));

	}

	@Test
	void lca2() {

		var res = ClassUtil.lca(Float.class, Integer.class);

		assertTrue(ClassUtil.isExtends(res, Float.class));
		assertTrue(ClassUtil.isExtends(res, Integer.class));

	}

}
