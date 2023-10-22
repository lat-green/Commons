package com.greentree.common.graphics.sgl.input;

import com.greentree.common.graphics.sgl.input.DoublePoisitionAction.DoublePoisitionListener;
import com.greentree.commons.action.MultiAction;

/** @author Arseny Latyshev */
public class DoublePoisitionAction extends MultiAction<DoublePoisitionListener> {
	
	public void action(int x1, int y1, int x2, int y2) {
		for(var l : listeners)
			l.action(x1, y1, x2, y2);
	}
	
	@FunctionalInterface
	public interface DoublePoisitionListener {
		
		public void action(int x1, int y1, int x2, int y2);
		
	}
}
