package com.greentree.commons.action;

import com.greentree.commons.action.container.MultiContainer;

public abstract class MultiAction<L> extends ContainerAction<L, MultiContainer<L>> {

    public MultiAction() {
        super(new MultiContainer<>());
    }

}
