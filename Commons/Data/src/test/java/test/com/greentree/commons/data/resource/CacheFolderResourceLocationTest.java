package test.com.greentree.commons.data.resource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.greentree.commons.data.resource.location.CacheResourceLocation;
import com.greentree.commons.data.resource.location.RootFileResourceLocation;
import com.greentree.commons.data.resource.location.RootUrlResourceLocation;

public class CacheFolderResourceLocationTest {
	
	private static final String HELLO = "Hello";
	
	@Test
	void test1() throws IOException {
		final var in_dir = Files.createTempDirectory("in").toFile();
		final var out_dir = Files.createTempDirectory("out").toFile();
		final var in = new RootFileResourceLocation(in_dir);
		in_dir.deleteOnExit();
		final var a = new File(in_dir, "a.txt");
		
		try(final var aout = new FileOutputStream(a)) {
			aout.write(HELLO.getBytes());
		}
		
		final var out = new RootFileResourceLocation(out_dir);
		out.getRoot().deleteOnExit();
		
		final var cache = new CacheResourceLocation(in, out);
		
		final var res1 = cache.getResource("a.txt");
		
		try(final var aout = new FileOutputStream(a)) {
			aout.write(HELLO.getBytes());
		}
		
		final var res2 = cache.getResource("a.txt");
		
		assertTrue(res2.lastModified() >= res1.lastModified());
		
		in_dir.delete();
		out_dir.delete();
	}
	
	@Test
	void test2() throws IOException {
		final var out_dir = Files.createTempDirectory("out").toFile();
		out_dir.deleteOnExit();
		
		final var in = new RootUrlResourceLocation(
				new URL("https://avatars.githubusercontent.com/u/"));
		
		final var out = new RootFileResourceLocation(out_dir);
		
		final var cache = new CacheResourceLocation(in, out);
		
		Assertions.assertTimeout(Duration.ofMillis(5000), ()-> {
			cache.getResource("30156490");
		});
		Assertions.assertTimeout(Duration.ofMillis(500), ()-> {
			cache.getResource("30156490");
		});
		
		out_dir.delete();
	}
	
}
