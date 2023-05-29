package com.greentree.commons.logger;


public enum ConstLogLayer implements LogLayer {
	
	INFO(ConsoleColor.YELLOW),
	DEBUG(ConsoleColor.BLUE),
	FATAL(ConsoleColor.RED),
	ERROR(ConsoleColor.RED),
	WARN(ConsoleColor.GREEN),
	;

	private final ConsoleColor color;
	
	private ConstLogLayer(ConsoleColor color) {
		this.color = color;
	}
	
	@Override
	public ConsoleColor color() {
		return color;
	}
	

}
