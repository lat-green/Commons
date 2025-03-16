open module commons.context {
    requires kotlin.stdlib;
    requires commons.injector;
    requires commons.graph;
    exports com.greentree.commons.context;
    exports com.greentree.commons.context.annotation;
    exports com.greentree.commons.context.argument;
    exports com.greentree.commons.context.environment;
    exports com.greentree.commons.context.layer;
    exports com.greentree.commons.context.provider;
}
