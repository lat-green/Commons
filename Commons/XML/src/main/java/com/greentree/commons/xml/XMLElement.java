package com.greentree.commons.xml;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class XMLElement implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Collection<XMLElement> children;
    private final Map<String, String> attributes;
    private final String name, content;

    public XMLElement(Collection<XMLElement> children, Map<String, String> attributes, String name,
                      String content) {
        this.children = new ArrayList<>(children);
        this.attributes = new HashMap<>(attributes);
        this.name = name;
        this.content = content;
    }

    public String getAttribute(final String name, final String def) {
        final String value = getAttribute(name);
        if (value == null || value.length() == 0)
            return def;
        return value;
    }

    public String getAttribute(final String name) {
        return attributes.get(name);
    }

    public String[] getAttributeNames() {
        var names = attributes.keySet();
        return names.toArray(new String[names.size()]);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public XMLElement getChildren(final String name) {
        final Iterable<XMLElement> xmls = getChildrens(name);
        for (var xml : xmls)
            return xml;
        return null;
    }

    public Collection<XMLElement> getChildrens(final String name) {
        return getChildren().stream().filter(xml -> xml.getName().equals(name))
                .collect(Collectors.toList());
    }

    public Collection<XMLElement> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(children);
        result = 31 * result + Objects.hashCode(attributes);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(content);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XMLElement that = (XMLElement) o;
        return Objects.equals(children, that.children)
               && Objects.equals(attributes, that.attributes)
               && Objects.equals(name, that.name)
               && Objects.equals(content.trim(), that.content.trim());
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("<").append(name);
        for (var n : attributes.keySet())
            res.append(" ").append(n).append("=\"").append(getAttribute(n)).append("\"");
        if (getChildren().isEmpty()) {
            if (getContent().isBlank())
                res.append("/>");
            else {
                res.append(">");
                if (getContent().indexOf('\n') != -1)
                    res.append("\n\t").append(getContent()).append("\n");
                else
                    res.append(getContent());
                res.append("</").append(name).append(">");
            }
        } else {
            res.append(">");
            if (getContent().indexOf('\n') != -1)
                res.append("\n\t").append(getContent()).append("\n");
            else
                res.append(getContent());
            for (final XMLElement e : getChildren())
                res.append(("\n" + e.toString()).replace("\n", "\n\t"));
            res.append("\n</").append(name).append(">");
        }
        return res.toString();
    }

}
