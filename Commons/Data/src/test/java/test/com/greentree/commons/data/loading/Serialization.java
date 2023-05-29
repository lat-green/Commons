package test.com.greentree.commons.data.loading;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Deprecated
public class Serialization {
	
	static Stream<Object> objects() {
		return Stream.of("55", new Data(1, 2), new Data(-8, 5));
	}
	
	@ParameterizedTest
	@MethodSource("objects")
	void doubleSerialization(Object obj) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream aout = new ByteArrayOutputStream();
		
		ObjectOutputStream out = new ObjectOutputStream(aout);
		out.writeObject(obj);
		out.close();
		
		byte[] bytes = aout.toByteArray();
		
		aout.close();
		
		ByteArrayInputStream ain = new ByteArrayInputStream(bytes);
		ObjectInputStream in = new ObjectInputStream(ain);
		
		Object copy = in.readObject();
		
		in.close();
		ain.close();
		
		assertEquals(obj, copy, "class: " + obj.getClass());
	}
	
}

class Data implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int x, y;
	
	public Data(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Data)) {
			return false;
		}
		Data other = (Data) obj;
		return x == other.x && y == other.y;
	}
	
	
	
	@Override
	public String toString() {
		return "Data [" + x + ", " + y + "]";
	}
}
