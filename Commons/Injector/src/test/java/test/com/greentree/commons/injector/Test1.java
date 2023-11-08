package test.com.greentree.commons.injector;

import com.greentree.commons.injector.AllFieldDependencyScanner;
import com.greentree.commons.injector.InjectionContainerImpl;
import com.greentree.commons.injector.Injector;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test1 {

    @Test
    void test1() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        var container = new InjectionContainerImpl();
        container.put("x", 1);
        container.put("y", 2);
        container.put("z", 3);
        var sc = new AllFieldDependencyScanner();
        var injector = new Injector(container, sc);
        var a = injector.newInstance(A.class);
        a.inject();
        assertEquals(a.value(), new A(1, 2, 3));
    }

}
