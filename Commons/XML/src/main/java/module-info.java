open module commons.xml {
    requires transitive commons.graph;
    requires transitive java.xml;
    requires transitive kotlin.stdlib;
    exports com.greentree.commons.xml;
    exports com.greentree.commons.xml.parser;
    exports com.greentree.commons.xml.parser.token;
}
