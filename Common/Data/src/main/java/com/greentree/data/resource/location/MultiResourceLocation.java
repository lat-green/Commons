package com.greentree.data.resource.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.greentree.common.util.exception.MultiException;
import com.greentree.common.util.iterator.IteratorUtil;
import com.greentree.data.resource.Resource;


public final class MultiResourceLocation implements NamedResourceLocation, Iterable<ResourceLocation> {

	private static final long serialVersionUID = 1L;

	public static final MultiResourceLocation EMPTY = new MultiResourceLocation();

	private final Iterable<ResourceLocation> locations;
	private MultiResourceLocation() {
		this(IteratorUtil.empty());
	}

	private MultiResourceLocation(Iterable<? extends ResourceLocation> locations) {
		this.locations = IteratorUtil.clone(locations);
	}

	public Builder builder() {
		return new Builder(this);
	}

	@Override
	public Iterable<String> getAllNames() {
		final Collection<Iterable<String>> names = new ArrayList<>();
		for(var location : locations)
			if(location instanceof NamedResourceLocation)
				names.add(((NamedResourceLocation) location).getAllNames());
		return IteratorUtil.union(names);
	}

	@Override
	public Resource getResource(String name) {
		return getResourceLiniar(name);
	}

	@Override
	public Iterator<ResourceLocation> iterator() {
		return locations.iterator();
	}

	@Override
	public String toString() {
		return "ResourceLocationCollection [" + IteratorUtil.toString(locations) + "]";
	}


	private Resource getResourceLiniar(String name) {
		final var exepts = new ArrayList<Throwable>();
		for(final var loc : locations) try {
			final var v = loc.getResource(name);
			if(v == null)
				continue;
			return v;
		}catch (Exception e) {
			exepts.add(e);
		}
		if(exepts.isEmpty())
			return null;
		throw throwResourceNotFound(name, exepts);
	}

	private MultiException throwResourceNotFound(String name, Collection<Throwable> exepts) {
		return new MultiException("Resource \"" + name + "\" not found in " + IteratorUtil.toString(locations), exepts);
	}

	public static final class Builder {

		private final Collection<ResourceLocation> list = new HashSet<>();
		private final MultiResourceLocation main;


		public Builder(MultiResourceLocation main) {
			this.main = main;
		}

		public Builder add(ResourceLocation location) {
			if(location instanceof MultiResourceLocation)
				synchronized(list) {
					for(var l : (MultiResourceLocation)location)list.add(l);
				}
			else
				synchronized(list) {
					list.add(location);
				}
			return this;
		}

		public MultiResourceLocation build() {
			synchronized(list) {
				if(list.isEmpty()) return main;
				return new MultiResourceLocation(list);
			}
		}

	}

}
