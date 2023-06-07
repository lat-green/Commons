open module commons.image {
    requires transitive commons.util;
    requires transitive java.desktop;
    exports com.greentree.commons.image.loader;
    exports com.greentree.commons.image.writer;
    exports com.greentree.commons.image.image;
    exports com.greentree.commons.image;
}
