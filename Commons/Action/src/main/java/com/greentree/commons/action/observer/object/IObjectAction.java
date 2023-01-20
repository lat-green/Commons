package com.greentree.commons.action.observer.object;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.ObjectObserver;

public interface IObjectAction<T> extends ObjectObservable<T>, ObjectObserver<T> {

}
