package test.com.greentree.commons.xml;

import com.greentree.commons.xml.parser.MyXMLParser;
import com.greentree.commons.xml.parser.XMLParser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyXMLParserTest extends XMLParserTest {

    @Override
    public void runXMLParser(@NotNull Function1<? super XMLParser, Unit> block) {
        block.invoke(MyXMLParser.INSTANCE);
    }

    @Test
    public void replaceReference() {
        assertEquals(
                MyXMLParser.INSTANCE.replaceReference("name = &#x27;Angular&#x27;;. showMore = false;"),
                "name = 'Angular';. showMore = false;"
        );
    }

}
