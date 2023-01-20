package com.greentree.action;

@FunctionalInterface
public interface ListenerCloser extends AutoCloseable {
	
	static final class Null implements ListenerCloser {
		
		private Null() {
		}
		
		@Override
		public void close() {
		}
		
	}
	
	ListenerCloser EMPTY = new Null();
	
	@Override
	void close();
	
	static ListenerCloser merge(ListenerCloser... closers) {
		return ()-> {
			for(var c : closers)
				c.close();
		};
	}
	
	static ListenerCloser merge(Iterable<? extends ListenerCloser> closers) {
		return ()-> {
			for(var c : closers)
				c.close();
		};
	}
	
}
