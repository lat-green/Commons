package com.greentree.action.observer.object;

import com.greentree.action.observable.ObjectObservable;
import com.greentree.action.observer.ObjectObserver;

public interface IObjectAction<T> extends ObjectObservable<T>, ObjectObserver<T> {

}
