package com.greentree.commons.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class LoggerFactory {
	private final LoggerPolitics politics;

	public LoggerPolitics getPolitics() {
		return politics;
	}
	
	public LoggerFactory(LoggerPolitics politics) {
		this.politics = politics;
	}

	public Logger create(PrintStream stream) {
		return new Logger(politics, stream);
	}

	public Logger create(OutputStream stream) {
		if(stream instanceof PrintStream)
			return create((PrintStream)stream);
		return new Logger(politics, new PrintStream(stream));
	}
	
	public Logger create(File file) throws FileNotFoundException {
		return create(new FileOutputStream(file));
	}
	
}
