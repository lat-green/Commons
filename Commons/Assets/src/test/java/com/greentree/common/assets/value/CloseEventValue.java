package com.greentree.common.assets.value;

import com.greentree.commons.assets.value.ProxyValue;
import com.greentree.commons.assets.value.Value;


public class CloseEventValue<T> extends ProxyValue<T> {
	
	private static final long serialVersionUID = 1L;
	private final Runnable onClose;
	
	public CloseEventValue(Value<T> source, Runnable onClose) {
		super(source);
		this.onClose = onClose;
	}
	
	@Override
	public void close() {
		super.close();
		onClose.run();
	}
	
}
