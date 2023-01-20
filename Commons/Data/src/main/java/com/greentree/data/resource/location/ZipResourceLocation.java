package com.greentree.data.resource.location;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.greentree.common.util.exception.WrappedException;
import com.greentree.data.FileUtil;
import com.greentree.data.resource.NullResourceAction;
import com.greentree.data.resource.Resource;
import com.greentree.data.resource.ResourceAction;


public class ZipResourceLocation implements NamedResourceLocation, ResourceLocation {
	
	private static final long serialVersionUID = 1L;
	private final Resource zip;
	private final Collection<String> names = new ArrayList<>();
	
	public ZipResourceLocation(Resource resource) throws IOException {
		zip = resource;
		try(final var in = zip.open();final var zip_in = new ZipInputStream(in)) {
			ZipEntry entry;
			while((entry = zip_in.getNextEntry()) != null) {
				final var name = entry.getName();
				names.add(name);
			}
		}
	}
	
	@Override
	public Iterable<String> getAllNames() {
		return names;
	}
	
	@Override
	public Resource getResource(String name) {
		try(final var in = zip.open();final var zip_in = new ZipInputStream(in)) {
			
			final var entry = found(name, zip_in);
			
			return new ZipEntryResource(entry);
		}catch(IOException e) {
			return null;
		}
	}
	
	private boolean equals(String name, String entryName) {
		if(entryName.charAt(entryName.length() - 1) == '/')
			return false; // is folder
			
		final var nf = FileUtil.getFileNameWithOutFolder(entryName);
		final var n0 = FileUtil.getFileNameWithOutExtension(nf);
		
		if(n0.isBlank())
			if(nf.isBlank())
				return name.equals(entryName);
			else
				return name.equals(nf) || name.equals(entryName);
		else
			return name.equals(n0) || name.equals(nf) || name.equals(entryName);
	}
	
	private ZipEntry found(String name, ZipInputStream zip) throws IOException {
		ZipEntry entry;
		while((entry = zip.getNextEntry()) != null) {
			final var n = entry.getName();
			if(equals(name, n))
				return entry;
		}
		return null;
	}
	
	private void skipTo(ZipEntry entry, ZipInputStream zip) throws IOException {
		ZipEntry e;
		while((e = zip.getNextEntry()) != null)
			if(entry.getName().equals(e.getName()))
				return;
		throw new IllegalArgumentException();
	}
	
	public final class ZipEntryResource implements Resource {
		
		private static final long serialVersionUID = 1L;
		
		private final ZipEntry entry;
		
		public ZipEntryResource(ZipEntry entry) {
			this.entry = entry;
		}
		
		@Override
		public String getName() {
			return zip.getName() + File.separator + entry.getName();
		}
		
		@Override
		public long lastModified() {
			return entry.getLastModifiedTime().toMillis();
		}
		
		@Override
		public long length() {
			return entry.getSize();
		}
		
		@Override
		public InputStream open() {
			try(final var in = zip.open();final var zip_in = new ZipInputStream(in)) {
				skipTo(entry, zip_in);
				
				final var bytes = zip_in.readNBytes((int) entry.getSize());
				
				return new ByteArrayInputStream(bytes);
			}catch(IOException e) {
				throw new WrappedException(e);
			}
		}
		
		@Override
		public String toString() {
			return "ZipEntryResource [" + entry + " in " + zip + "]";
		}
		
		@Override
		public ResourceAction getAction() {
			return NullResourceAction.NULL_RESOURCE_ACTION;
		}
		
	}
	
}
