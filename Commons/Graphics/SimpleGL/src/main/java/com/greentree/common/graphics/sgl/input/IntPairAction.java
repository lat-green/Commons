package com.greentree.common.graphics.sgl.input;

import com.greentree.common.graphics.sgl.input.IntPairAction.IntPairListener;
import com.greentree.commons.action.MultiAction;

/** @author Arseny Latyshev */
public class IntPairAction extends MultiAction<IntPairListener> {
	
	public void action(int x, int y) {
		for(var l : listeners)
			l.action(x, y);
	}
	
	@FunctionalInterface
	public interface IntPairListener {
		
		public void action(int x, int y);
		
	}
}
