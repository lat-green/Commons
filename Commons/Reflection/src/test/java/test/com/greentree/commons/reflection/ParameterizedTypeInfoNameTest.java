package test.com.greentree.commons.reflection;

import com.greentree.commons.reflection.info.TypeInfoBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedTypeInfoNameTest {

    @Test
    void test() {
        var info = TypeInfoBuilder.getTypeInfo(ArrayList.class, String.class);
        assertEquals(info.getSimpleName(), "ArrayList<String>");
    }

}
