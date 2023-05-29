package com.greentree.commons.data.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.greentree.commons.util.exception.WrappedException;

public interface Resource extends Serializable {
	
	/** @return Action or {@link NullResourceAction#NULL_RESOURCE_ACTION} */
	ResourceAction getAction();
	
	InputStream open();
	
	String getName();
	
	long lastModified();
	
	/** @return length or -1 if unknown */
	long length();
	
	default void writeTo(IOResource result) {
		try(final var out = result.openWrite();final var in = open()) {
			in.transferTo(out);
		}catch(IOException e) {
			throw new WrappedException(e);
		}
	}
	
	default void writeTo(IOResource result, long lastRead) {
		final var m = lastModified();
		if(m == 0 || m > lastRead)
			writeTo(result);
	}
	
}
