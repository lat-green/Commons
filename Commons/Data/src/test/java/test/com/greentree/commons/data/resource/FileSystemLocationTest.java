package test.com.greentree.commons.data.resource;

import com.greentree.commons.data.resource.location.ClassLoaderResourceLocation;
import com.greentree.commons.data.resource.location.RecursionFileSystemLocation;
import com.greentree.commons.data.resource.location.RootFileResourceLocation;
import com.greentree.commons.data.resource.location.ZipResourceLocation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** testing class FileSystemLocation
 * @author Arseny Latyshev
 *
 * @see RootFileResourceLocation */
public class FileSystemLocationTest {
	
	
	
	static void assertEqualsArrays(byte[] a, byte[] b) {
		var len = a.length;
		
		assertEquals(len, b.length);
		
		for(var i = 0; i < len; i++)
			assertEquals(a[i], b[i]);
		
	}
	
	static <T> void assertEqualsArrays(T[] a, T[] b) {
		var len = a.length;
		
		assertEquals(len, b.length);
		
		for(var i = 0; i < len; i++)
			assertEquals(a[i], b[i]);
		
	}
	
	@TempDir
	Path tempDir;
	
	@ParameterizedTest
	@ValueSource(strings = {"test","Hello","test1"})
	void foundSomething(String text) throws IOException {
		final var file = text + ".txt";
		Path path = tempDir.resolve(file);
		try(FileWriter w = new FileWriter(path.toFile())) {
			w.write(text);
		}catch(Exception e) {
			throw e;
		}
		
		final var location = new RecursionFileSystemLocation(tempDir);
		final var url = location.getResource(file);
		assertNotNull(url);
		
		try(final var inputStream = url.open();) {
			assertNotNull(inputStream);
			assertEquals(text, new String(inputStream.readAllBytes()));
		}
	}

    @Disabled
	@Test
	void zip() throws IOException {
		final var ress = new ClassLoaderResourceLocation(FileSystemLocationTest.class);
		var zip = ress.getResource("test.zip");
		
		final var zipLoc = new ZipResourceLocation(zip);
		
		final var res = zipLoc.getResource("1.txt");
		try(final var in = res.open()) {
			assertEqualsArrays(new byte[]{'1'}, in.readAllBytes());
		}
	}
}
