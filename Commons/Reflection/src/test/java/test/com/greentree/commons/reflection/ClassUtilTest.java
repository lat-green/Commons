package test.com.greentree.commons.reflection;

import com.greentree.commons.reflection.ClassUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * testing ClassUtil.isExtends
 *
 * @author User
 */
public class ClassUtilTest {

    @Test
    void isExtends() {
        assertFalse(ClassUtil.isExtends(Float.class, Integer.class));
        assertFalse(ClassUtil.isExtends(Integer.class, Float.class));
        assertFalse(ClassUtil.isExtends(Integer.class, Object.class));
        assertTrue(ClassUtil.isExtends(Object.class, Integer.class));

    }

}
