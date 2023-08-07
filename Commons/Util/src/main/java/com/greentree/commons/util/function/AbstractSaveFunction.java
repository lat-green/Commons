package com.greentree.commons.util.function;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSaveFunction<T, R> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<T, R> cache = new ConcurrentHashMap<>();

    public synchronized final R applyRaw(T t) {
        R result;
        result = cache.get(t);
        if (validRaw(result))
            return result;
        result = create(t);
        if (!valid(Objects.requireNonNull(result)))
            throw new UnsupportedOperationException("not valid " + result);
        cache.put(t, result);
        init(result);
        return result;
    }

    private boolean validRaw(R result) {
        return result != null && valid(result);
    }

    protected abstract R create(T t);

    protected boolean valid(R result) {
        return true;
    }

    protected void init(R result) {
    }

    public synchronized final void clear() {
        cache.clear();
    }

    @Override
    public String toString() {
        return "SaveFunction [cache=" + cache + "]";
    }

}
