package com.greentree.common.util.collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.collection.MultyExtendClassTree;

public class MultyExtendClassTreeTest {

	@Test
	void checkAdd() {
		MultyExtendClassTree tree = new MultyExtendClassTree();

		var a = new A();

		tree.add(a);
		tree.add(a);

		tree.add(new B());
		tree.add(new C());
		tree.add(K.class);

		assertTrue(tree.containsClass(A.class));
		assertTrue(tree.containsClass(B.class));
		assertTrue(tree.containsClass(C.class));
		assertTrue(tree.containsClass(K.class));

		assertTrue(tree.contains(K.class));
	}

	@Test
	void checkRemoveObject() {
		MultyExtendClassTree tree = new MultyExtendClassTree();

		var a = new A();

		assertFalse(tree.containsClass(A.class));
		tree.add(a);
		assertTrue(tree.containsClass(A.class));
		tree.remove(a);
		assertFalse(tree.containsClass(A.class));
	}

	@Test
	void checkRemoveOne() {
		MultyExtendClassTree tree = new MultyExtendClassTree();

		assertFalse(tree.containsClass(A.class));
		tree.add(new A());
		assertTrue(tree.containsClass(A.class));
		tree.removeOne(A.class);
		assertFalse(tree.containsClass(A.class));
	}


}
class A implements K {

	@Override
	public String toString() {
		return "A";
	}

}

class B extends A implements K {

	@Override
	public String toString() {
		return "B";
	}
}
class C extends B {

	@Override
	public String toString() {
		return "C";
	}
}
interface K {

}