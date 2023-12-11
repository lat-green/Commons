open module commons.action {
    requires transitive commons.util;
    requires kotlin.stdlib;
    exports com.greentree.commons.action;
    exports com.greentree.commons.action.observable;
    exports com.greentree.commons.action.observer;
    exports com.greentree.commons.action.observer.run;
    exports com.greentree.commons.action.observer.object;
    exports com.greentree.commons.action.observer.pair;
    exports com.greentree.commons.action.observer.type;
    exports com.greentree.commons.action.observer.integer;
    exports com.greentree.commons.action.container;
}
