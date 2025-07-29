package test.com.greentree.commons.xml;

import com.greentree.commons.xml.parser.MyXMLParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyXMLParserTest {

    @Test
    public void replaceReference() {
        assertEquals(
                "name = 'Angular';. showMore = false;",
                MyXMLParser.INSTANCE.replaceReference("name = &#x27;Angular&#x27;;. showMore = false;")
        );
    }

}
