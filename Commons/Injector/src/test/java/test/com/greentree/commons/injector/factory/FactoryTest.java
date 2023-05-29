package test.com.greentree.commons.injector.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.greentree.commons.injector.factory.IntProperty;
import com.greentree.commons.injector.factory.MapInstanceProperties;
import com.greentree.commons.injector.factory.ObjectFactories;

public class FactoryTest {
	
	@Test
	void assertEqualsPointDefaultConstructor() {
		final var point = ObjectFactories.newInstance(Point.class);
		assertEquals(point, new Point());
	}
	
	@Test
	void assertEqualsPointConstructor() {
		final var point = ObjectFactories.newInstance(Point.class,
				new MapInstanceProperties(Map.of("x", new IntProperty(5), "y", new IntProperty(4))));
		assertEquals(point, new Point(5, 4));
	}
	
	private static final class Point {
		
		private int x, y;
		
		public Point() {
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(!(obj instanceof Point))
				return false;
			var other = (Point) obj;
			return x == other.x && y == other.y;
		}
		
		@Override
		public int hashCode() {
			final var prime = 31;
			var result = 1;
			result = prime * result + Objects.hash(x, y);
			return result;
		}
		
		@Override
		public String toString() {
			return "Point [" + x + ", " + y + "]";
		}
		
	}
}
