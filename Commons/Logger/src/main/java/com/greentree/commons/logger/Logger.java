package com.greentree.commons.logger;

import java.io.PrintStream;
import java.util.function.Supplier;

public final class Logger {

	private final LoggerPolitics politics;
	private final PrintStream print;
	
	public LoggerPolitics getPolitics() {
		return politics;
	}
	
	public PrintStream getPrintStream() {
		return print;
	}

	public Logger(LoggerPolitics politics, PrintStream print) {
		this.print = print;
		this.politics = politics;
	}
	
	public final void log(LogLayer layer, String message) {
		log(layer, () -> message);
	}
	
	public final void log(LogLayer layer, Supplier<String> message) {
		politics.log(print, layer, message);
		print.append('\n');
		print.flush();
	}
	
}
