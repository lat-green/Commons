open module commons.math {
    requires transitive kotlin.stdlib;
    requires transitive commons.action;
    requires transitive org.joml;
    exports com.greentree.commons.math;
    exports com.greentree.commons.math.quaternion;
    exports com.greentree.commons.math.vector;
    exports com.greentree.commons.math.vector.ord;
    exports com.greentree.commons.math.vector.list;
}