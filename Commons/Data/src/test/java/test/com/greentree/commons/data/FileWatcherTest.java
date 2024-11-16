package test.com.greentree.commons.data;

import com.greentree.commons.data.FileWatcher;
import com.greentree.commons.tests.ExecuteCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileWatcherTest {

    private static final int REPEAT = 200;

    File dir;

    @RepeatedTest(REPEAT)
    void getFileAction_OnModify() throws Exception {
        final var file = new File(dir, "test.txt");
        try (var counter = new ExecuteCounter(1);
             var lc = FileWatcher.INSTANCE.onFileModify(file).addListener(counter)) {
            assertTrue(file.createNewFile());
            try (final var out = new FileOutputStream(file)) {
                out.write("Hello".getBytes());
            }
            assertTrue(file.delete());
            FileWatcher.INSTANCE.take();
        }
    }

    @RepeatedTest(REPEAT)
    void getFileAction_OnCreate() throws Exception {
        final var file = new File(dir, "test.txt");
        try (var counter = new ExecuteCounter(1);
             var lc = FileWatcher.INSTANCE.onFileCreate(file).addListener(counter)) {
            assertTrue(file.createNewFile());
            FileWatcher.INSTANCE.take();
        }
    }

    @RepeatedTest(REPEAT)
    void getFileAction_OnDelete() throws Exception {
        final var file = new File(dir, "test.txt");
        try (var counter = new ExecuteCounter(1);
             var lc = FileWatcher.INSTANCE.onFileDelete(file).addListener(counter)) {
            assertTrue(file.createNewFile());
            assertTrue(file.delete());
            FileWatcher.INSTANCE.take();
        }
    }

    @BeforeEach
    void init() throws IOException {
        dir = Files.createTempDirectory("junit").toFile();
    }

}
