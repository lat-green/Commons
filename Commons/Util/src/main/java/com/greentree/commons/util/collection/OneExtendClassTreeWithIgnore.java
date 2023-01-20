package com.greentree.commons.util.collection;

import java.lang.annotation.Annotation;

@Deprecated
public class OneExtendClassTreeWithIgnore<E> extends OneExtendClassTree<E> {
	
	private static final long serialVersionUID = 1L;
	private final Class<? extends Annotation> ignore;
	
	public OneExtendClassTreeWithIgnore(Class<E> root, Class<? extends Annotation> ignore) {
		super(root);
		this.ignore = ignore;
	}
	
	public OneExtendClassTreeWithIgnore(int size, Class<E> root,
			Class<? extends Annotation> ignore) {
		super(size, root);
		this.ignore = ignore;
	}
	
	@Override
	protected boolean isIgnore(Class<?> clazz) {
		//		if(clazz.isAnnotationPresent(ignore)) {
		//			System.out.println("ignore "+clazz);
		//			return true;
		//		}
		//		return false;
		return clazz.isAnnotationPresent(ignore);
	}
	
}
