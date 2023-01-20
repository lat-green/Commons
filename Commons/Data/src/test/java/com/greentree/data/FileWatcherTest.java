package com.greentree.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import com.greentree.commons.data.FileWatcher;

public class FileWatcherTest {
	
	private static final int SLEEP = 5;
	private static final int REPEAT = 20;
	
	File dir;
	
	@RepeatedTest(REPEAT)
	void getFolderAction_OnCreate() throws Exception {
		final var actionDir = FileWatcher.getFolderAction(dir);
		final var file = new File(dir, "test.txt");
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnCreateAbsolute().addListener(f-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		assertTrue(check[0]);
		lc.close();
	}
	
	@RepeatedTest(REPEAT)
	void getFolderAction_OnDelete() throws Exception {
		final var actionDir = FileWatcher.getFolderAction(dir);
		final var file = new File(dir, "test.txt");
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnDeleteAbsolute().addListener(f-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		assertTrue(file.delete());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		assertTrue(check[0]);
		lc.close();
	}
	
	@RepeatedTest(REPEAT)
	void getFileAction_OnModify() throws Exception {
		final var file = new File(dir, "test.txt");
		final var actionDir = FileWatcher.getFileAction(file);
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnModify().addListener(()-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		
		try(final var out = new FileOutputStream(file)) {
			out.write("Hello".getBytes());
		}
		
		assertTrue(file.delete());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		
		assertTrue(check[0]);
		lc.close();
	}
	
	@RepeatedTest(REPEAT)
	void getFileAction_OnCreate() throws Exception {
		final var file = new File(dir, "test.txt");
		final var actionDir = FileWatcher.getFileAction(file);
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnCreate().addListener(()-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		assertTrue(check[0]);
		lc.close();
	}
	
	@RepeatedTest(REPEAT)
	void getFileAction_OnDelete() throws Exception {
		final var file = new File(dir, "test.txt");
		final var actionDir = FileWatcher.getFileAction(file);
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnDelete().addListener(()-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		assertTrue(file.delete());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		assertTrue(check[0]);
		lc.close();
	}
	
	@RepeatedTest(REPEAT)
	void getFolderAction_OnModify() throws Exception {
		final var actionDir = FileWatcher.getFolderAction(dir);
		final var file = new File(dir, "test.txt");
		
		final var check = new boolean[1];
		
		final var lc = actionDir.getOnModifyAbsolute().addListener(f-> {
			assertFalse(check[0]);
			check[0] = true;
		});
		
		assertTrue(file.createNewFile());
		
		try(final var out = new FileOutputStream(file)) {
			out.write("Hello".getBytes());
		}
		
		assertTrue(file.delete());
		
		Thread.sleep(SLEEP);
		
		FileWatcher.pollEvents();
		
		assertTrue(check[0]);
		lc.close();
	}
	
	@BeforeEach
	void init() throws IOException {
		dir = Files.createTempDirectory("junit").toFile();
	}
	
	
}
