package com.greentree.common.graph.algorithm.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.greentree.common.graph.Graph;
import com.greentree.common.util.cortege.Pair;

public class AStarPathFinder {
	
	public static <V> VertexPath<V> get(Graph<? extends V> g,
			BiFunction<? super V, ? super V, ? extends Number> arc_length,
			Function<? super V, ? extends Number> approximate_dis_to_end, V begin, V end) {
		final PriorityQueue<Pair<Double, V>> queue = new PriorityQueue<>(
				Comparator.comparing(p->p.first));
		queue.add(new Pair<>(0d, begin));
		Map<V, V> parent = new HashMap<>();
		Map<V, Double> dis_to_start = new HashMap<>();
		for(V v : g)
			dis_to_start.put(v, Double.MAX_VALUE);
		dis_to_start.put(begin, 0d);
		while(!queue.isEmpty()) {
			final double cur_d;
			V v;
			{
				var p = queue.remove();
				cur_d = p.first;
				v = p.seconde;
			}
			if(v.equals(end)) {
				List<V> res = new ArrayList<>();
				for(; v != null; v = parent.get(v))
					res.add(v);
				Collections.reverse(res);
				return new VertexPath<>(res);
			}
			if(cur_d > dis_to_start.get(v))
				continue;
			for(V to : g.getJoints(v)) {
				var len = arc_length.apply(v, to).doubleValue();
				var dis = len + approximate_dis_to_end.apply(to).doubleValue()
						+ dis_to_start.get(v);
				if(dis < dis_to_start.get(to)) {
					dis_to_start.put(to, dis);
					parent.put(to, v);
					queue.add(new Pair<>(dis_to_start.get(to), to));
				}
			}
		}
		return null;
	}
	
}
