package com.greentree.commons.data;

import com.greentree.commons.action.CloseRunnable;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.data.resource.ResourceAction;
import com.greentree.commons.data.resource.ResourceActionImpl;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.commons.util.exception.WrappedException;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

    private final static Map<Path, FolderResourceAction> folders = FunctionAutoGenerateMap
            .build(FolderResourceAction::new);

    private FileWatcher() {
    }

    public static ResourceAction getFileAction(File file) {
        return getFileAction(file.toPath());
    }

    public static ResourceAction getFileAction(Path file) {
        return new FileResourceAction(folders.get(file.getParent()), file);
    }

    public static FolderResourceAction getFolderAction(File dir) {
        assert dir.exists() && dir.isDirectory() || !dir.exists() : dir + " is not file";
        return getFolderAction(dir.toPath());
    }

    public static FolderResourceAction getFolderAction(Path dir) {
        return folders.get(dir);
    }

    public static void pollEvents() {
        for (var a : folders.values())
            a.pollEvents();
    }

    public static void takeEvents() {
        for (var a : folders.values())
            a.takeEvents();
    }

    private static final class FileResourceAction extends ResourceActionImpl {

        private static final long serialVersionUID = 1L;

        private final ListenerCloser lc1, lc2, lc3;

        public FileResourceAction(FolderResourceAction action, Path file) {
            lc1 = action.getOnCreateAbsolute().addListener(f -> {
                if (f.equals(file))
                    eventCreate();
            });
            lc2 = action.getOnModifyAbsolute().addListener(f -> {
                if (f.equals(file))
                    eventModify();
            });
            lc3 = action.getOnDeleteAbsolute().addListener(f -> {
                if (f.equals(file))
                    eventDelete();
            });
            var cleaner = Cleaner.create();
            cleaner.register(this, new CloseRunnable(lc1));
            cleaner.register(this, new CloseRunnable(lc2));
            cleaner.register(this, new CloseRunnable(lc3));
        }

    }

    public static final class FolderResourceAction {

        private transient final EventAction<Path> onCreate = new EventAction<>();
        private transient final EventAction<Path> onModify = new EventAction<>();
        private transient final EventAction<Path> onDelete = new EventAction<>();
        private transient final EventAction<Path> onCreateAbsolute = new EventAction<>();
        private transient final EventAction<Path> onModifyAbsolute = new EventAction<>();
        private transient final EventAction<Path> onDeleteAbsolute = new EventAction<>();
        private final WatchService watchService;
        private final Path dir;

        public FolderResourceAction(Path dir) throws IOException {
            try {
                watchService = FileSystems.getDefault().newWatchService();
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            dir.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            this.dir = dir;
        }

        public Path getDir() {
            return dir;
        }

        public ObjectObservable<Path> getOnCreate() {
            return onCreate;
        }

        public ObjectObservable<Path> getOnCreateAbsolute() {
            return onCreateAbsolute;
        }

        public ObjectObservable<Path> getOnDelete() {
            return onDelete;
        }

        public ObjectObservable<Path> getOnDeleteAbsolute() {
            return onDeleteAbsolute;
        }

        public ObjectObservable<Path> getOnModify() {
            return onModify;
        }

        public ObjectObservable<Path> getOnModifyAbsolute() {
            return onModifyAbsolute;
        }

        public boolean pollEvents() {
            try {
                final var key = watchService.poll();
                if (key != null)
                    return solveKey(key);
                return true;
            } catch (Exception e) {
                throw new WrappedException(e);
            }
        }

        private boolean solveKey(WatchKey key) {
            for (var event : key.pollEvents())
                try {
                    final var path = (Path) event.context();
                    final var absolute = dir.resolve(path);
                    if (event.kind() == ENTRY_CREATE) {
                        onCreate.event(path);
                        onCreateAbsolute.event(absolute);
                    }
                    if (event.kind() == ENTRY_MODIFY) {
                        onModify.event(path);
                        onModifyAbsolute.event(absolute);
                    }
                    if (event.kind() == ENTRY_DELETE) {
                        onDelete.event(path);
                        onDeleteAbsolute.event(absolute);
                    }
                } catch (Exception e) {
                    throw new WrappedException(e);
                }
            return key.reset();
        }

        public boolean takeEvents() {
            try {
                final var key = watchService.take();
                solveKey(key);
                return key != null;
            } catch (Exception e) {
                throw new WrappedException(e);
            }
        }

        @Override
        public String toString() {
            return "FolderResourceAction [" + dir + "]";
        }

    }

}
