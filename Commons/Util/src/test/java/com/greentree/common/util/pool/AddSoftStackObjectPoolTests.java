package com.greentree.common.util.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.pool.AddSoftStackObjectPool;

public class AddSoftStackObjectPoolTests {


	private static int VECTOR_COUNT = 0;
	private static AddSoftStackObjectPool<Vector> new_pool() {
		return new AddSoftStackObjectPool<>();
	}

	private static Vector new_vec() {
		return new Vector(VECTOR_COUNT++);
	}
	@Test
	void test1() {
		final var pool = new_pool();
		final var v1 = new_vec();
		pool.add(v1);
		try(final var ref = pool.get();) {
			final var v2 = ref.get();
			assertEquals(v1, v2);
		}
	}

	@Test
	void test2() {
		final var pool = new_pool();
		final var v1 = new_vec();
		final var v2 = new_vec();
		pool.add(v1);
		pool.add(v2);
		try(final var ref = pool.get();) {
			final var v3 = ref.get();
			try(final var ref2 = pool.get();) {
				final var v4 = ref2.get();
				assertEquals(v1, v4);
			}
			assertEquals(v2, v3);
		}
	}
	private final static class Vector {
		public int x;

		public Vector(int x) {
			this.x = x;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null || getClass() != obj.getClass()) return false;
			Vector other = (Vector) obj;
			return x == other.x;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Vector [x=");
			builder.append(x);
			builder.append("]");
			return builder.toString();
		}
	}

}
