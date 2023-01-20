package com.greentree.commons.graph.algorithm.path;

import static com.greentree.commons.graph.algorithm.path.GraphPathUtil.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import com.greentree.commons.graph.DirectedArc;

public class VertexPath<V> extends ArrayList<V> {
	
	private static final long serialVersionUID = 1L;
	
	private final BiFunction<? super V, ? super V, ? extends Number> distance;
	
	public VertexPath() {
		this(BASE_DISTANCE);
	}
	
	public VertexPath(BiFunction<? super V, ? super V, ? extends Number> distance) {
		this.distance = distance;
	}
	
	public VertexPath(Collection<? extends V> c) {
		this(c, BASE_DISTANCE);
	}
	
	public VertexPath(Collection<? extends V> c,
			BiFunction<? super V, ? super V, ? extends Number> distance) {
		super(c);
		this.distance = distance;
	}
	
	public VertexPath(int initialCapacity) {
		this(initialCapacity, BASE_DISTANCE);
	}
	
	public VertexPath(int initialCapacity,
			BiFunction<? super V, ? super V, ? extends Number> distance) {
		super(initialCapacity);
		this.distance = distance;
	}
	
	@SafeVarargs
	public VertexPath(final V... arr) {
		this(BASE_DISTANCE, arr);
	}
	
	@SafeVarargs
	public VertexPath(BiFunction<? super V, ? super V, ? extends Number> distance, final V... arr) {
		Collections.addAll(this, arr);
		this.distance = distance;
	}
	
	public VertexPath(final V first, final Collection<? extends V> arg0) {
		this(first, arg0, BASE_DISTANCE);
	}
	
	public VertexPath(final V first, final Collection<? extends V> arg0,
			BiFunction<? super V, ? super V, ? extends Number> distance) {
		add(first);
		addAll(arg0);
		this.distance = distance;
	}
	
	public double length() {
		var dis = 0D;
		var p = get(0);
		for(var i = 1; i < size(); i++) {
			final var v = get(i);
			dis += distance.apply(p, v).doubleValue();
			p = v;
		}
		return dis;
	}
	
	public List<DirectedArc<V>> toJoints() {
		List<DirectedArc<V>> res = new ArrayList<>();
		var v1 = get(0);
		for(var i = 1; i < size(); i++) {
			final var v2 = get(i);
			res.add(new DirectedArc<>(v1, v2));
			v1 = v2;
		}
		return res;
	}
	
	
	
}
