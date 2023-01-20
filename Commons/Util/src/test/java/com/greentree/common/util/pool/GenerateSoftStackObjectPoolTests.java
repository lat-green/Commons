package com.greentree.common.util.pool;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.pool.GenerateSoftStackObjectPool;

public class GenerateSoftStackObjectPoolTests {


	private static int VECTOR_COUNT = 0;
	private static GenerateSoftStackObjectPool<Vector> new_pool() {
		return new GenerateSoftStackObjectPool<>() {

			@Override
			public Vector generate() {
				return new_vec();
			}

		};
	}

	private static Vector new_vec() {
		return new Vector(VECTOR_COUNT++);
	}
	@Test
	void test1() {
		final var pool = new_pool();
		try(final var ref = pool.get();) {
			final var v = ref.get();
			assertNotNull(v);
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
