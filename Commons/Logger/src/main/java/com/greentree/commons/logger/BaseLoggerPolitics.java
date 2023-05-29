package com.greentree.commons.logger;

import java.io.PrintStream;
import java.util.Date;
import java.util.function.Supplier;


public class BaseLoggerPolitics implements LoggerPolitics {
	
	@Override
	public void log(PrintStream print, LogLayer layer, Supplier<? extends String> message) {
		print.append(new Date().toString());
		print.append(' ');
		print.append('[');
		print.append(layer.color().toString());
		print.append(layer.name());
		print.append(ConsoleColor.RESET.toString());
		print.append(']');
		print.append(' ');
		print.append('[');
		print.append(Thread.currentThread().getName());
		print.append(']');
		print.append(' ');
		print.append(message.get());
	}
	
}
