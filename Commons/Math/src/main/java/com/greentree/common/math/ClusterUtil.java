package com.greentree.common.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiFunction;

import com.greentree.common.util.collection.FunctionAutoGenerateMap;
import com.greentree.common.util.iterator.IteratorUtil;

public class ClusterUtil {

	public static <T> Iterable<Iterable<T>> clusterDBSCAN(Iterable<? extends T> iterable, BiFunction<? super T, ? super T, ? extends Double> p) {
		final var m = (int)Math.sqrt(IteratorUtil.size(iterable)) - 1;
		var s = 0.0;
		for(var a : iterable)
			for(var b : iterable)
				s += Math.sqrt(p.apply(a, b));
		s = Math.sqrt(s);
		return clusterDBSCAN(iterable, p, s, m);
	}
	
	public static <T> Iterable<Iterable<T>> clusterDBSCAN(Iterable<? extends T> iterable, BiFunction<? super T, ? super T, ? extends Double> p, double e, int m) {
		final var main = new ArrayList<T>();
		{
			for(var a : iterable) {
				int i = 0;
				for(var b : iterable) if(a != b) {
					if(main.contains(b))
						if(p.apply(a, b) < e)
							break;
						else
							continue;
					if(p.apply(a, b) < e) {
						i++;
						if(i >= m) {
							main.add(a);
							break;
						}
					}
				}
			}
		}
		final var accessible = new FunctionAutoGenerateMap<T, Collection<T>>(t -> new CopyOnWriteArraySet<>());
		{
			for(var a : main)
				for(var b : iterable)
					if(p.apply(a, b) < e)
						accessible.get(a).add(b);
			for(var a : main)
				for(var b : accessible.get(a))
					for(var c : accessible.get(b))
						accessible.get(a).add(c);
					
		}
		return IteratorUtil.iterable(c -> {
    		for(var v : main)
    			c.accept(accessible.get(v));
		});
	}
	
}
