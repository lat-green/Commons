package test.com.greentree.commons.data.resource;

import com.greentree.commons.data.resource.location.ClassLoaderResourceLocation;
import com.greentree.commons.data.resource.location.RootFileResourceLocation;
import com.greentree.commons.data.resource.location.ZipResourceLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static com.greentree.commons.data.resource.location.RecursionFileSystemLocationKt.RecursionFileSystemLocation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * testing class FileSystemLocation
 *
 * @author Arseny Latyshev
 * @see RootFileResourceLocation
 */
class FileSystemLocationTest {

    @TempDir
    Path tempDir;

    static <T> void assertEqualsArrays(T[] a, T[] b) {
        var len = a.length;
        assertEquals(len, b.length);
        for (var i = 0; i < len; i++)
            assertEquals(a[i], b[i]);

    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "Hello", "test1"})
    void foundSomething(String text) throws IOException {
        final var file = text + ".txt";
        Path path = tempDir.resolve(file);
        try (FileWriter w = new FileWriter(path.toFile())) {
            w.write(text);
        } catch (Exception e) {
            throw e;
        }
        final var location = RecursionFileSystemLocation(tempDir);
        final var url = location.getFileResource(file);
        assertNotNull(url);
        try (final var inputStream = url.open()) {
            assertNotNull(inputStream);
            assertEquals(text, new String(inputStream.readAllBytes()));
        }
    }

    @Test
    void zip() throws IOException {
        final var ress = new ClassLoaderResourceLocation(FileSystemLocationTest.class);
        var zip = ress.getResource("test.zip");
        final var zipLoc = new ZipResourceLocation(zip);
        final var res = zipLoc.getFileResource("1.txt");
        try (final var in = res.open()) {
            assertEqualsArrays(new byte[]{'1'}, in.readAllBytes());
        }
    }

    static void assertEqualsArrays(byte[] a, byte[] b) {
        var len = a.length;
        assertEquals(len, b.length);
        for (var i = 0; i < len; i++)
            assertEquals(a[i], b[i]);

    }

}
