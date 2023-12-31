open module commons.serialization {
    requires transitive kotlin.stdlib;
    requires transitive com.google.gson;
    requires transitive commons.util;
    requires transitive commons.reflection;
    exports com.greentree.commons.serialization;
    exports com.greentree.commons.serialization.data;
    exports com.greentree.commons.serialization.descriptor;
    exports com.greentree.commons.serialization.serializer;
}
