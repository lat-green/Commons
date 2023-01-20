package com.greentree.commons.util.collection;

import java.io.Serializable;
import java.util.HashSet;

import com.greentree.commons.util.collection.CostList.CostElement;

public class CostList<T> extends HashSet<CostElement<T>> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public boolean add(T e, float cost) {
		return add(new CostElement<>(e, cost));
	}
	
	public T get() {
		var sum = getCostSum();
		sum = (float) (sum * Math.random());
		for(var e : this) {
			sum -= e.cost;
			if(sum <= 0f)
				return e.element;
		}
		throw new RuntimeException();
	}
	
	public double getCostSum() {
		return stream().mapToDouble(x->x.cost).reduce(0, (a, b)->a + b);
	}
	
	public static final class CostElement<T> {
		
		private final double cost;
		private final T element;
		
		public CostElement(final T e, final double cost) {
			this.element = e;
			this.cost = cost;
		}
		
		public double getCost() {
			return cost;
		}
		
		public T getElement() {
			return element;
		}
	}
	
	
}
