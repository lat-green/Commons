package test.com.greentree.commons.reflection;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TypeInfoTest {

    private static final class FloatArrayList extends ArrayList<Float> {

        private static final long serialVersionUID = 1L;

    }

    @Test
    void equalsBuildTypeInfo() {
        final var a = TypeInfoBuilder.getTypeInfo(Integer.class);
        final var b = TypeInfoBuilder.getTypeInfo(Integer.class);
        assertEquals(a, b);

    }

    @Test
    void GArrayList_GCollection_isSuper_true() {
        final var a = TypeInfoBuilder.getTypeInfo(ArrayList.class, String.class);
        final var b = TypeInfoBuilder.getTypeInfo(Collection.class, String.class);
        assertTrue(a.isChildFor(b));
    }

    @Test
    void ArrayList_GCollection_isSuper_false() {
        final var a = TypeInfoBuilder.getTypeInfo(ArrayList.class);
        final var b = TypeInfoBuilder.getTypeInfo(Collection.class, String.class);
        assertFalse(a.isChildFor(b));
    }

    @Test
    void GArrayList_Collection_isSuper_false() {
        final var a = TypeInfoBuilder.getTypeInfo(FloatArrayList.class);
        final var b = TypeInfoBuilder.getTypeInfo(Collection.class, String.class);
        assertFalse(a.isChildFor(b));
    }

    @Test
    void ArrayList_Collection_isSuper_false() {
        final var a = TypeInfoBuilder.getTypeInfo(ArrayList.class, String.class);
        final var b = TypeInfoBuilder.getTypeInfo(Collection.class, Integer.class);
        assertFalse(a.isChildFor(b));
    }

    @SuppressWarnings("rawtypes")
    @Test
    void getInterfacesOfClassInfoArrayList() {
        final var integer = TypeInfoBuilder.getTypeInfo(ArrayList.class);
        final var interfaces = integer.getInterfaces();
        final var interfaces_set1 = new HashSet<TypeInfo<? super ArrayList>>();
        final var interfaces_set2 = new HashSet<TypeInfo<? super ArrayList>>();
        Collections.addAll(interfaces_set1, interfaces);
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(Serializable.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(Cloneable.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(RandomAccess.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(List.class));
        assertEquals(interfaces_set1, interfaces_set2);
    }

    @SuppressWarnings("rawtypes")
    @Test
    void getInterfacesOfParameterizedTypeInfoArrayList() {
        final var integer = TypeInfoBuilder.getTypeInfo(ArrayList.class, Integer.class);
        final var interfaces = integer.getInterfaces();
        final var interfaces_set1 = new HashSet<TypeInfo<? super ArrayList>>();
        final var interfaces_set2 = new HashSet<TypeInfo<? super ArrayList>>();
        Collections.addAll(interfaces_set1, interfaces);
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(Serializable.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(Cloneable.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(RandomAccess.class));
        interfaces_set2.add(TypeInfoBuilder.getTypeInfo(List.class, Integer.class));
        assertEquals(interfaces_set1, interfaces_set2);
    }

    @Test
    void getSuperClassOfClassInfoArrayList() {
        final var arrayList = TypeInfoBuilder.getTypeInfo(ArrayList.class);
        final var abstractList = TypeInfoBuilder.getTypeInfo(AbstractList.class);
        final var superOfArrayList = arrayList.getSuperType();
        assertEquals(abstractList, superOfArrayList);

    }

    @Test
    void getSuperClassOfClassInfoInteger() {
        final var integer = TypeInfoBuilder.getTypeInfo(Integer.class);
        final var number1 = TypeInfoBuilder.getTypeInfo(Number.class);
        final var number2 = integer.getSuperType();
        assertEquals(number1, number2);

    }

    @Test
    void getSuperClassOfParameterizedTypeInfoArrayList() {
        final var arrayList = TypeInfoBuilder.getTypeInfo(ArrayList.class, Integer.class);
        final var abstractList = TypeInfoBuilder.getTypeInfo(AbstractList.class, Integer.class);
        final var superOfArrayList = arrayList.getSuperType();
        assertEquals(superOfArrayList, abstractList);

    }

}
