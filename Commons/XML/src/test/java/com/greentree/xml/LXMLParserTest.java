package com.greentree.xml;

import com.greentree.commons.xml.parser.LXMLParser;
import com.greentree.commons.xml.parser.XMLParser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class LXMLParserTest extends XMLParserTest {

    @Override
    public void runXMLParser(@NotNull Function1<? super XMLParser, Unit> block) {
        block.invoke(LXMLParser.INSTANCE);
    }

}
