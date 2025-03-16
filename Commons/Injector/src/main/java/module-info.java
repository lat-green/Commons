open module commons.injector {
    requires transitive kotlin.stdlib;
    requires transitive commons.reflection;
    requires transitive commons.annotation;
    requires transitive kotlin.reflect;
    requires transitive commons.util;
    exports com.greentree.commons.injector;
}
