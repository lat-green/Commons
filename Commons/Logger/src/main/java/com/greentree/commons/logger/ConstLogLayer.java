package com.greentree.commons.logger;


public enum ConstLogLayer implements LogLayer{
	INFO,
	DEBUG,
	FATAL,
	ERROR,
	WARN,
	;

	@Override
	public String getName() {
		return name();
	}

}
