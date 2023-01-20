package com.greentree.common.logger;


public interface LoggerPolitics {

	boolean isThreadWrite();
	boolean isDataWrite();
	boolean isEnabled(LogLayer layer);
	
	
}
