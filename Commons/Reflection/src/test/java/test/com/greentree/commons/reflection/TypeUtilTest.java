package test.com.greentree.commons.reflection;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.reflection.info.TypeInfoKt;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.util.cortege.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeUtilTest {

    static Stream<TypeInfo<? extends Comparable<?>>> comparables() {
        return Stream.of(Integer.class, Float.class, File.class).map(TypeInfoKt::TypeInfo);
    }

    static Stream<Pair<TypeInfo<?>, TypeInfo<?>>> collections() {
        return Stream.of(
                TypeInfoBuilder.getTypeInfo(ArrayList.class),
                TypeInfoBuilder.getTypeInfo(ArrayList.class, ArrayList.class),
                TypeInfoBuilder.getTypeInfo(ArrayList.class, TypeInfoBuilder.getTypeInfo(ArrayList.class, ArrayList.class))
        ).map(t -> new Pair<>(TypeInfoBuilder.getTypeInfo(ArrayList.class, t), t));
    }

    @MethodSource("comparables")
    @ParameterizedTest
    <T extends Comparable<T>> void getType_Comparable(TypeInfo<T> type) {
        final var actualType = TypeUtil.getFirstArgument(type, Comparable.class);
        assertEquals(type, actualType);
    }

    @MethodSource("collections")
    @ParameterizedTest
    <T> void getType_Collections(Pair<TypeInfo<? extends Collection<T>>, TypeInfo<T>> type) {
        final var actualType = TypeUtil.getFirstArgument(type.first, Collection.class);
        assertEquals(type.seconde, actualType);
    }

}
