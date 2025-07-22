package test.com.greentree.commons.xml;

import com.greentree.commons.xml.parser.SAXXMLParser;
import com.greentree.commons.xml.parser.XMLParser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class SAXXMLParserTest extends XMLParserTest {

    @Override
    public void runXMLParser(@NotNull Function1<? super XMLParser, Unit> block) {
        block.invoke(SAXXMLParser.INSTANCE);
    }

}
