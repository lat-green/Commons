package com.greentree.commons.action.observer.type;

import java.util.function.Function;

@Deprecated(forRemoval = true)
public class FuncTypedObjectAction<T, E> extends TypedObjectAction<T, E> implements IFuncTypedObjectAction<T, E> {

    private static final long serialVersionUID = 1L;

    private final Function<? super E, ? extends T> toType;

    public FuncTypedObjectAction(Function<? super E, ? extends T> toType) {
        super();
        this.toType = toType;
    }

    @Override
    public T getType(E t) {
        return toType.apply(t);
    }

}
