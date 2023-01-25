package com.greentree.commons.assets.value;

public class CloseEventValue<T> extends ProxyValue<T> {
	
	private static final long serialVersionUID = 1L;
	private transient final Runnable onClose;
	
	public CloseEventValue(Value<T> source, Runnable onClose) {
		super(source);
		this.onClose = onClose;
	}
	
	@Override
	public void close() {
		super.close();
		if(onClose != null)
			onClose.run();
	}
	
	@Override
	public Value<T> copy() {
		return new CloseEventValue<>(source.copy(), onClose);
	}
	
}
