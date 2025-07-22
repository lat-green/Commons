package test.com.greentree.commons.xml;

import com.greentree.commons.xml.parser.ANTLR4XMLParser;
import com.greentree.commons.xml.parser.XMLParser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class ANTLR4XMLParserTest extends XMLParserTest {

    @Override
    public void runXMLParser(@NotNull Function1<? super XMLParser, Unit> block) {
        block.invoke(ANTLR4XMLParser.INSTANCE);
    }

}
