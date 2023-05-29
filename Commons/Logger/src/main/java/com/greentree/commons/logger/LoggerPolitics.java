package com.greentree.commons.logger;

import java.io.PrintStream;
import java.util.function.Supplier;

public interface LoggerPolitics {

	void log(PrintStream print, LogLayer layer, Supplier<? extends String> message);
	
	
}
