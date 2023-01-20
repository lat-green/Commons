package com.greentree.action.container;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.greentree.action.ListenerCloser;


public final class MultiContainer<L> implements ListenerContainer<L>, Externalizable {
	
	private static final long serialVersionUID = 1L;
	
	private Collection<L> listeners = new WeakMultiSet<>() {
		
		protected void weakRemove() {
			throw new ListenerNotCloseException();
		};
		
	};
	
	private Collection<L> _add = new ArrayList<>();
	private Collection<L> _remove = new ArrayList<>();
	
	@Override
	public ListenerCloser add(L listener) {
		synchronized(_add) {
			_add.add(listener);
		}
		return new MultiContainerListenerCloser<>(_remove, listener);
	}
	
	@Override
	public void clear() {
		synchronized(listeners) {
			updateListeners();
			listeners.clear();
		}
	}
	
	public boolean isEmpty() {
		synchronized(listeners) {
			updateListeners();
			return listeners.isEmpty();
		}
	}
	
	@Override
	public Iterator<L> iterator() {
		synchronized(listeners) {
			updateListeners();
			return listeners.iterator();
		}
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	}
	
	@Override
	public int size() {
		synchronized(listeners) {
			updateListeners();
			return listeners.size();
		}
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
	}
	
	private void updateListeners() {
		synchronized(_add) {
			listeners.addAll(_add);
			_add.clear();
		}
		synchronized(_remove) {
			listeners.removeAll(_remove);
			_remove.clear();
		}
	}
	
	private record MultiContainerListenerCloser<L>(Collection<L> _remove, L listener)
			implements ListenerCloser {
		
		@Override
		public void close() {
			synchronized(_remove) {
				_remove.add(listener);
			}
		}
		
	}
	
}
