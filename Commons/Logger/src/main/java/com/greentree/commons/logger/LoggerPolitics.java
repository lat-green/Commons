package com.greentree.commons.logger;


public interface LoggerPolitics {

	boolean isThreadWrite();
	boolean isDataWrite();
	boolean isEnabled(LogLayer layer);
	
	
}
