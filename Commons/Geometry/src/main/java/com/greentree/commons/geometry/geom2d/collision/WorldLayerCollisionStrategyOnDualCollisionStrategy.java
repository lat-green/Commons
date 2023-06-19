package com.greentree.commons.geometry.geom2d.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.greentree.commons.geometry.geom2d.collision.strategy.dual.DualCollisionStrategy;
import com.greentree.commons.util.collection.TransientTable;
import com.greentree.commons.util.cortege.Pair;
import com.greentree.commons.util.function.LambdaSaveFunction;
import com.greentree.commons.util.function.SaveFunction;

public class WorldLayerCollisionStrategyOnDualCollisionStrategy<T extends Collidable2D>
		implements WorldLayerCollisionStrategy<T> {
	
	private final Map<T, Integer> layers = new HashMap<>();
	private final SaveFunction<Integer, Collection<T>> layersCollection = new LambdaSaveFunction<>(
			e->new ArrayList<>());
	
	private final TransientTable<Integer, Boolean> is;
	private final DualCollisionStrategy<T> strategy;
	
	public WorldLayerCollisionStrategyOnDualCollisionStrategy(TransientTable<Integer, Boolean> is,
			DualCollisionStrategy<T> strategy) {
		this.is = is;
		this.strategy = strategy;
	}
	
	@Override
	public void add(int layer, T shape) {
		layers.put(shape, layer);
		layersCollection.apply(layer).add(shape);
	}
	
	@Override
	public boolean contains(T shape) {
		return layers.containsKey(shape);
	}
	
	@Override
	public Collection<Pair<T, T>> getCollisionContacts() {
		Set<Pair<T, T>> res = new HashSet<>();
		for(var a : layers())
			for(var b : layers())
				if(a < b)
					break;
				else
					if(isCollisionLayers(a, b))
						res.addAll(strategy.getCollisionContact(layersCollection.apply(a),
								layersCollection.apply(b)));
		return res;
	}
	
	@Override
	public boolean isCollisionLayers(int a, int b) {
		Boolean r = is.get(a, b);
		if(r != null)
			return r;
		is.put(a, b, false);
		return false;
	}
	
	@Override
	public Collection<Integer> layers() {
		return layers.values();
	}
	
	@Override
	public void remove(T shape) {
		layersCollection.apply(layers.remove(shape)).remove(shape);
	}
	
}
