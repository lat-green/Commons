open module commons.serialization {
    requires transitive com.fasterxml.jackson.databind;
    requires transitive commons.xml;
    requires commons.context;
    requires commons.annotation;
    requires commons.util;
    requires kotlin.reflect;
    exports com.greentree.commons.serialization.context;
    exports com.greentree.commons.serialization.format;
    exports com.greentree.commons.serialization.serializator;
    exports com.greentree.commons.serialization.serializator.accuracy;
    exports com.greentree.commons.serialization.serializator.filter;
    exports com.greentree.commons.serialization.serializator.manager;
    exports com.greentree.commons.serialization.serializator.provider;
    exports com.greentree.commons.serialization.serializator.type;
}
