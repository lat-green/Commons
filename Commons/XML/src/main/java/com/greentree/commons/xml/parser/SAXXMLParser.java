package com.greentree.commons.xml.parser;

import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.xml.XMLElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class SAXXMLParser {

    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    static {
    }

    private SAXXMLParser() {
    }

    public static XMLElement parse(final InputStream in) throws ParserConfigurationException, IOException {
        final DocumentBuilder builder = SAXXMLParser.factory.newDocumentBuilder();
        builder.setErrorHandler(new DefaultHandler());
        var is = new InputSource(in);
        final Document doc;
        try {
            doc = builder.parse(is);
        } catch (Throwable e) {
            throw new WrappedException(e);
        }
        return build(doc.getDocumentElement());
    }

    public static XMLElement build(final Element xmlElement) throws IOException, ParserConfigurationException {
        String name = xmlElement.getTagName();
        Collection<XMLElement> childrens = new ArrayList<>();
        final NodeList list = xmlElement.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            var item = list.item(i);
            if (item instanceof Element) childrens.add(build((Element) item));
        }
        StringBuilder sbuilder = new StringBuilder();
        for (int i = 0; i < list.getLength(); ++i) {
            var item = list.item(i);
            if (item instanceof Text) sbuilder.append(item.getNodeValue());
        }
        String content = sbuilder.toString().replace("\n", "").replace("\t", "");
        Map<String, String> attributes = new HashMap<>();
        var map = xmlElement.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            var a = map.item(i);
            var n = a.getNodeName();
            var v = a.getTextContent();
            attributes.put(n, v);
        }
        return new XMLElement(childrens, attributes, name, content);
    }

}
