package test.com.greentree.commons.action.container;

import com.greentree.commons.action.container.MultiContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiContainerSerializableTest {

    @Test
    void test1() throws ClassNotFoundException, IOException {
        final var c1 = new MultiContainer<Runnable>();
        try (final var lc = c1.add(Assertions::fail)) {
            assertFalse(c1.isEmpty());
            final var c2 = (MultiContainer<?>) deser(ser(c1));
            assertTrue(c2.isEmpty());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T deser(byte[] ser) throws IOException, ClassNotFoundException {
        try (final var bout = new ByteArrayInputStream(ser)) {
            try (final var oout = new ObjectInputStream(bout)) {
                return (T) oout.readObject();
            }
        }
    }

    private byte[] ser(Object obj) throws IOException {
        try (final var bout = new ByteArrayOutputStream()) {
            try (final var oout = new ObjectOutputStream(bout)) {
                oout.writeObject(obj);
            }
            return bout.toByteArray();
        }
    }

}
