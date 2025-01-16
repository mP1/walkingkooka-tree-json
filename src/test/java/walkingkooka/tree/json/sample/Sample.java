/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.json.sample;

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

import java.nio.charset.Charset;
import java.util.Base64;

@walkingkooka.j2cl.locale.LocaleAware
public class Sample {

    public static void main(final String[] args) {
        final Sample sample = new Sample();

        sample.testEncodeDecodeRoundtrip();
        sample.testParseJson();
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

    private void checkEquals(final Object expected,
                             final Object actual) {
        System.out.println(expected);
        System.out.println(actual);
    }
}
