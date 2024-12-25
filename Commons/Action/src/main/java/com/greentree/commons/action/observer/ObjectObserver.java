package com.greentree.commons.action.observer;

public interface ObjectObserver<T> extends Observer {

    void event(T t);

}
