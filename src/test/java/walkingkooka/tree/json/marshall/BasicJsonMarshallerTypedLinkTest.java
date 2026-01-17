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

package walkingkooka.tree.json.marshall;

import jdk.jshell.spi.ExecutionControl.RunException;
import org.junit.jupiter.api.Test;
import walkingkooka.net.Url;
import walkingkooka.net.header.Link;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.MissingPropertyJsonNodeException;
import walkingkooka.tree.json.UnknownPropertyJsonNodeException;

public final class BasicJsonMarshallerTypedLinkTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedLink, Link> {

    @Test
    public void testUnmarshallStringFails() {
        this.unmarshallFailed(
            JsonNode.string("fails!"),
            RunException.class
        );
    }

    @Test
    public void testUnmarshallObjectEmptyFails() {
        this.unmarshallFailed(
            JsonNode.object(),
            MissingPropertyJsonNodeException.class
        );
    }

    @Test
    public void testUnmarshallHrefNonStringFails() {
        this.unmarshallFailed(
            JsonNode.object()
                .set(
                    BasicJsonMarshallerTypedLink.HREF_JSON_PROPERTY,
                    123
                ),
            RunException.class
        );
    }

    @Test
    public void testUnmarshallUnknownPropertyFails() {
        this.unmarshallFailed(
            JsonNode.object()
                .set(
                    JsonPropertyName.with("unknown-property"),
                    123
                ),
            UnknownPropertyJsonNodeException.class
        );
    }

    @Test
    public void testUnmarshallLink() {
        final String href = "https://example.com";
        this.unmarshallAndCheck(
            JsonNode.object()
                .set(
                    BasicJsonMarshallerTypedLink.HREF_JSON_PROPERTY,
                    href
                ),
            Link.with(
                Url.parse(href)
            )
        );
    }

    @Test
    public void testMarshallLink() {
        this.marshallAndCheck(
            "<https://example.com>",
            "{\"href\": \"https://example.com\"}"
        );
    }

    @Test
    public void testMarshallRel() {
        this.marshallAndCheck(
            "<https://example.com>;type=text/plain;rel=previous",
            "{\"href\": \"https://example.com\", \"rel\": \"previous\", \"type\": \"text/plain\"}"
        );
    }

    private void marshallAndCheck(final String link,
                                  final String json) {
        this.marshallAndCheck(
            Link.parse(link)
                .get(0),
            JsonNode.parse(json)
        );
    }


    @Override
    BasicJsonMarshallerTypedLink marshaller() {
        return BasicJsonMarshallerTypedLink.instance();
    }

    @Override
    Link value() {
        return Link.with(
            Url.parseAbsolute("https://example.com/path123")
        );
    }

    @Override
    JsonNode node() {
        return JsonNode.parse(
            "{\"href\": \"https://example.com/path123\"}"
        );
    }

    @Override
    Link jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "link";
    }

    @Override
    Class<Link> marshallerType() {
        return Link.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLink> type() {
        return BasicJsonMarshallerTypedLink.class;
    }
}
