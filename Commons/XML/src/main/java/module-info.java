open module commons.xml {
    requires transitive commons.util;
    requires transitive java.xml;
    requires kotlin.stdlib;
    exports com.greentree.commons.xml;
    exports com.greentree.commons.xml.parser;
}
