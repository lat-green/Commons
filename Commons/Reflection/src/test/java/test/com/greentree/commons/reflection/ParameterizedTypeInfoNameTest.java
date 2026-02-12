package test.com.greentree.commons.reflection;

import com.greentree.commons.reflection.info.TypeInfoBuilder;
import kotlin.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedTypeInfoNameTest {

    @Test
    void test() {
        var info = TypeInfoBuilder.getTypeInfo(ArrayList.class, TypeInfoBuilder.getTypeInfo(Pair.class, String.class, Integer.class));
        assertEquals(info.getSimpleName(), "ArrayList");
        assertEquals(info.getName(), "java.util.ArrayList");
        assertEquals(info.getTypeName(), "java.util.ArrayList<kotlin.Pair<java.lang.String, java.lang.Integer>>");
    }

}
