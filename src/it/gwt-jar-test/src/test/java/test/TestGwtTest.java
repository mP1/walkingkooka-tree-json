package test;

import com.google.gwt.junit.client.GWTTestCase;

import java.nio.charset.Charset;
import java.util.Base64;

import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;

@walkingkooka.j2cl.locale.LocaleAware
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

    public void testEncodeDecodeRoundtrip() {
        final String data = "abc123def456";
        final Charset charset = Charset.forName("UTF-8");

        final String encoded = Base64.getEncoder().encodeToString(data.getBytes(charset));
        this.checkEquals(
            data,
            new String(
                Base64.getDecoder()
                    .decode(encoded),
                charset
            )
        );
    }

    public void testParseJson() {
        final JsonNode graph = JsonNode.object()
            .set(
                JsonPropertyName.with("string"),
                JsonNode.string("abc")
            ).set(
                JsonPropertyName.with("number"),
                JsonNode.number(Double.parseDouble("12.5"))
            ).set(
                JsonPropertyName.with("boolean"),
                JsonNode.booleanNode(true)
            ).set(
                JsonPropertyName.with("null"),
                JsonNode.nullNode()
            ).set(
                JsonPropertyName.with("array"),
                JsonNode.array()
            );

        this.checkEquals(
            graph,
            JsonNode.parse(graph.toString())
        );
    }
}
