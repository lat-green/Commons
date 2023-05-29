package com.greentree.commons.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.greentree.commons.util.InputStreamUtil;

public abstract class FileUtil {
	
	public static void checkDirectory(File directory) {
		if(directory.exists()) {
			if(!directory.isDirectory())
				throw new IllegalArgumentException("is not directory " + directory);
		}else
			throw new IllegalArgumentException("not exists " + directory);
	}
	
	public static void checkFile(File file) {
		if(file.exists()) {
			if(!file.isFile())
				throw new IllegalArgumentException("is not file " + file);
		}else
			throw new IllegalArgumentException("not exists " + file);
	}
	
	public static void clearDir(File dir) {
		delete(dir);
		dir.mkdirs();
	}
	
	public static void copyFileTree(File from, File to) throws IOException {
		Path srcPath = from.toPath();
		Path destPath = to.toPath();
		
		Files.walkFileTree(srcPath,
				new CopyDirVisitor(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING));
	}
	
	public static boolean delete(Path path) {
		return delete(path.toFile());
	}
	
	public static boolean delete(File file) {
		if(!file.exists())
			return true;
		if(file.isDirectory())
			for(var f : file.listFiles())
				if(!delete(f))
					return false;
		return file.delete();
	}
	
	
	public static void strongDelete(Path path) throws IOException {
		if(Files.exists(path))
			return;
		if(Files.isDirectory(path)) {
			final var list = Files.list(path).iterator();
			while(list.hasNext())
				strongDelete(list.next());
		}
		Files.delete(path);
	}
	
	public static void strongDelete(File file) throws IOException {
		strongDelete(file.toPath());
	}
	
	
	public static void dirToZip(File dir, File zipFile) throws FileNotFoundException, IOException {
		try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));) {
			zout.setLevel(9);
			for(File file : getAllFile(dir))
				addFileToZip0(file, FileUtil.getLocalPath(dir, file), zout);
		}
	}
	
	public static Collection<File> getAllFile(File file) {
		Collection<File> res = new ArrayList<>();
		Queue<File> dir = new LinkedList<>();
		if(file.isDirectory())
			dir.add(file);
		if(file.isFile())
			res.add(file);
		while(!dir.isEmpty()) {
			File d = dir.remove();
			for(File f : d.listFiles()) {
				if(f.isDirectory())
					dir.add(f);
				if(f.isFile())
					if("lnk".equals(FileUtil.getType(f)))
						try {
							WindowsShortcut w = new WindowsShortcut(f);
							dir.add(new File(w.getRealFilename()));
						}catch(IOException | ParseException e) {
							res.add(f);
						}
					else
						res.add(f);
			}
		}
		return res;
	}
	
	public static String delType(String path) {
		var l = getType(path).length();
		if(l == 0)
			return path;
		return path.substring(0, path.length() - l - 1);
	}
	
	public static String getFileNameWithOutExtension(String fileName) {
		int end = fileName.lastIndexOf('.');
		if(end == -1)
			return fileName;
		return fileName.substring(0, end);
	}
	
	public static String getFileNameWithOutFolder(File file) {
		return getFileNameWithOutFolder(file.getName());
	}
	
	public static String getFileNameWithOutFolder(String fileName) {
		int start1 = fileName.lastIndexOf('/');
		if(start1 == -1) {
			int start2 = fileName.lastIndexOf('\\');
			if(start2 == -1)
				return fileName;
			return fileName.substring(start2 + 1);
		}
		return fileName.substring(start1 + 1);
	}
	
	public static String getLocalPath(File dir, File file) {
		String a = dir.getAbsolutePath(), b = file.getAbsolutePath();
		int len = 0;
		final int min = Math.min(a.length(), b.length());
		while(len < min && a.charAt(len) == b.charAt(len))
			len++;
		return b.substring(len + 1);
	}
	
	public static String getName(File file) {
		return getName(file.getName());
	}
	
	public static String getName(Path file) {
		return getName(file.toFile());
	}
	
	public static String getName(String fileName) {
		return getFileNameWithOutExtension(getFileNameWithOutFolder(fileName));
	}
	
	public static String getType(File file) {
		return getType(file.getName());
	}
	
	public static String getType(Path file) {
		return getType(file.toFile());
	}
	
	public static String getType(String fileName) {
		int index = fileName.lastIndexOf('.');
		if(index == -1)
			return "";
		return fileName.substring(index + 1);
	}
	
	public static boolean isEmpty(File file) {
		if(file.isDirectory())
			return file.list().length == 0;
		throw new IllegalArgumentException("is not directory " + file.toString());
	}
	
	public static void openExploer(File file) throws IOException {
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "explorer " + file);
		builder.redirectErrorStream(true);
		builder.start();
	}
	
	public static void openExploer(Path path) throws IOException {
		openExploer(path.toFile());
	}
	
	public static String readFile(File file) throws IOException {
		try(FileInputStream in = new FileInputStream(file)) {
			return InputStreamUtil.readString(in);
		}
	}
	
	public static void write(File to, InputStream in) throws FileNotFoundException, IOException {
		try(final var out = new FileOutputStream(to)) {
			in.transferTo(out);
		}
	}
	
	
	public static void write(File to, String text) throws FileNotFoundException, IOException {
		write(to, new ByteArrayInputStream(text.getBytes()));
	}
	
	public static void zipToDir(File zip, File dir) throws FileNotFoundException, IOException {
		try(ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));) {
			ZipEntry entry;
			while((entry = zin.getNextEntry()) != null) {
				File file = new File(dir + "\\" + entry.getName());
				
				if(!file.exists()) {
					file.getParentFile().mkdir();
					file.createNewFile();
				}
				FileOutputStream fout = new FileOutputStream(file);
				for(int c = zin.read(); c != -1; c = zin.read())
					fout.write(c);
				
				fout.flush();
				zin.closeEntry();
				fout.close();
			}
		}
	}
	
	private static void addFileToZip0(File file, String name, ZipOutputStream zout)
			throws FileNotFoundException, IOException {
		try(FileInputStream fis = new FileInputStream(file)) {
			ZipEntry entry1 = new ZipEntry(name);
			zout.putNextEntry(entry1);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			zout.write(buffer);
		}
	}
	
	private static class CopyDirVisitor extends SimpleFileVisitor<Path> {
		
		private final Path fromPath;
		private final Path toPath;
		private final CopyOption copyOption;
		
		public CopyDirVisitor(Path fromPath, Path toPath, CopyOption copyOption) {
			this.fromPath = fromPath;
			this.toPath = toPath;
			this.copyOption = copyOption;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException {
			Path targetPath = toPath.resolve(fromPath.relativize(dir));
			if(!Files.exists(targetPath))
				Files.createDirectory(targetPath);
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.copy(file, toPath.resolve(fromPath.relativize(file)), copyOption);
			return FileVisitResult.CONTINUE;
		}
	}
	
}
