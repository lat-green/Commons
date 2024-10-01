package test.com.greentree.commons.data.resource;

import com.greentree.commons.data.resource.InMemoryFileResource;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryResourceTest {

    @Test
    void testWriteRead() throws IOException {
        var text = "text";
        var resource = new InMemoryFileResource("test.txt");
        try (var out = resource.openWrite()) {
            out.write(text.getBytes());
        }
        try (var in = resource.open()) {
            var t = new String(in.readAllBytes());
            assertEquals(t, text);
        }
    }

}