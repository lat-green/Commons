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
	
	public final void log(LogLayer layer, Supplier<String> str) {
		if(politics.isEnabled(layer)) {
			final var builder = new StringBuilder();
			if(politics.isDataWrite()) {
				builder.append("00:00:00");
				builder.append(' ');
			}
			if(politics.isThreadWrite()) {
				builder.append(Thread.currentThread());
				builder.append(' ');
			}
			builder.append(str.get());
			builder.append('\n');
			print.print(builder.toString());
		}
	}
	
}
