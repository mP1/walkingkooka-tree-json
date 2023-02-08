package test;

import com.google.gwt.junit.client.GWTTestCase;

import walkingkooka.j2cl.locale.LocaleAware;
import walkingkooka.tree.json.JsonNode;

@LocaleAware
public class TestGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testAssertEquals() {
        assertEquals(
                1,
                1
        );
    }

    public void testParse() {
        assertEquals(
                JsonNode.booleanNode(true),
                JsonNode.parse(
                        "true"
                )
        );
    }
}
