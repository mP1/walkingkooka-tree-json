package test;

import com.google.gwt.junit.client.GWTTestCase;

import java.nio.charset.Charset;
import java.util.Base64;

import walkingkooka.j2cl.locale.LocaleAware;
import walkingkooka.text.CharSequences;
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

    public void testEncodeDecodeRoundtrip() {
        final String data = "abc123def456";
        final Charset charset = Charset.forName("UTF-8");

        final String encoded = Base64.getEncoder().encodeToString(data.getBytes(charset));
        assertEquals("Started with " + CharSequences.quoteAndEscape(data) + " encoded: " + CharSequences.quoteAndEscape(encoded),
                data,
                new String(
                        Base64.getDecoder()
                                .decode(encoded),
                        charset
                )
        );
    }
}
