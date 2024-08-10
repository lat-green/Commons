open module commons.serialization {
    requires transitive kotlin.stdlib;
    requires transitive commons.util;
    requires transitive commons.reflection;
    requires transitive kotlin.reflect;
    requires transitive com.fasterxml.jackson.databind;
    exports com.greentree.commons.serialization.annotation;
    exports com.greentree.commons.serialization.data;
    exports com.greentree.commons.serialization.descriptor;
    exports com.greentree.commons.serialization.serializer;
}
