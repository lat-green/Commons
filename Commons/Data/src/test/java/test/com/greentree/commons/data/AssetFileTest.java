package test.com.greentree.commons.data;

import com.greentree.commons.data.file.DataFile;
import com.greentree.commons.util.array.TArray;
import com.greentree.commons.util.cortege.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetFileTest {

    static Stream<?> objs() {
        final var arr1 = new ArrayList<>();
        arr1.add("A");
        arr1.add(null);
        arr1.add(5);
        final var arr2 = new ArrayList<String>();
        arr2.add("A");
        arr2.add(null);
        final var map1 = new HashMap<>();
        map1.put("A", 5);
        map1.put(5, "A");
        map1.put("A", null);
        map1.put(5, null);
        return stream(null, "A", 5, new ArrayList<>(), new HashMap<>(), arr1, arr2, map1, new A(5, 5));
    }

    private static Stream<Object> stream(Object... objs) {
        return Arrays.stream(objs);
    }

    static Stream<Pair<?, ?>> pairs() {
        final var arr = new TArray<>("A");
        return Stream.of(new Pair<>("ABC", "BBC"), new Pair<>(5, 6), new Pair<>(arr, arr));
    }

    public record A(int a, float b) implements Serializable {

    }

    public static class TestPair<T1, T2> extends Pair<T1, T2> {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Pair other)) return false;
            return Objects.equals(seconde, other.seconde);
        }

        @Override
        public int hashCode() {
            return Objects.hash(seconde);
        }

        @Override
        public String toString() {
            return "TestPair " + seconde;
        }

    }

    @MethodSource("objs")
    @ParameterizedTest
    void test1(Object obj) throws IOException {
        final var bs = new DataFile();
        final var i = bs.append(obj);
        final var c = bs.get(i);
        assertEquals(c, obj);
    }

    @MethodSource("pairs")
    @ParameterizedTest
    void test2(Pair<?, ?> p) throws IOException {
        final var bs = new DataFile();
        final var a = p.first;
        final var b = p.seconde;
        final var i = bs.append(a, b);
        final var c = bs.get(a, i);
        assertEquals(c, b);
    }

    @Test
    void arrays() throws IOException {
        for (var obj : new Object[][]{
                {},
                {"A", "B"},
                {"A", null, 5},
                {4, 5},
        }) {
            final var bs = new DataFile();
            final var i = bs.append(obj);
            final var c = (Object[]) bs.get(i);
            assertArrayEquals(c, obj, Arrays.toString(c));
        }
    }

    @Test
    void doubleRef() throws IOException {
        final var a = new TestPair<>();
        final var b = new TestPair<>();
        a.seconde = "A";
        b.seconde = "B";
        a.first = b;
        b.first = a;
        final var bs = new DataFile();
        final var ai = bs.append(a);
        final var bi = bs.append(b);
        final var ac = bs.get(ai);
        final var bc = bs.get(bi);
        assertEquals(ac, a);
        assertEquals(bc, b);
    }

}
