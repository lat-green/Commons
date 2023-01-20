package com.greentree.common.math.vector.ord;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.common.math.vector.AbstractFloatVector;

/**
 * @author Arseny Latyshev
 *
 */
public class OrdsFloatVectorBuilder {
	
	private final Collection<FloatVectorOrd> ords = new ArrayList<>();

	public OrdsFloatVectorBuilder() {
    }
    
    public OrdsFloatVectorBuilder AddOrd(FloatVectorOrd ord) {
    	this.ords.add(ord);
    	return this;
    }

    public OrdsFloatVectorBuilder addValue() {
    	return AddOrd(new Value());
    }
    public OrdsFloatVectorBuilder addLink(AbstractFloatVector vec, int index) {
    	return AddOrd(new Link<>(vec, index));
    }

    public OrdsFloatVectorBuilder addValue(float value) {
    	return AddOrd(new Value(value));
    }
    public OrdsFloatVectorBuilder addConst(float value) {
    	return AddOrd(new Const(value));
    }
    
    public OrdsVectorf biuld() {
    	final var arr = new FloatVectorOrd[ords.size()];
    	ords.toArray(arr);
    	return new OrdsVectorf(arr);
    }
    
}
