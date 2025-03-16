open module commons.data {
    requires transitive commons.action;
    requires transitive commons.reflection;
    requires kotlin.stdlib;
    requires jdk.jfr;
    exports com.greentree.commons.data;
    exports com.greentree.commons.data.externalizable;
    exports com.greentree.commons.data.resource;
    exports com.greentree.commons.data.resource.location;
    exports com.greentree.commons.data.react;
}
