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

import org.junit.jupiter.api.Test;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

public final class BasicJsonMarshallerTypedJsonNodeTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedJsonNode, JsonNode> {

    @Test
    public void testUnmarshallArrayRemovesParent() {
        this.unmarshallRemovesParentCheck(
                JsonNode.array()
                        .appendChild(
                                JsonNode.string("1a")
                        )
        );
    }

    @Test
    public void testUnmarshallBooleanRemovesParent() {
        this.unmarshallRemovesParentCheck(
                JsonNode.booleanNode(true)
        );
    }

    @Test
    public void testUnmarshallNulRemovesParent() {
        this.unmarshallRemovesParentCheck(JsonNode.nullNode());
    }

    @Test
    public void testUnmarshallNumberRemovesParent() {
        this.unmarshallRemovesParentCheck(JsonNode.number(12.5));
    }

    @Test
    public void testUnmarshallStringRemovesParent() {
        this.unmarshallRemovesParentCheck(JsonNode.string("child-123"));
    }

    @Test
    public void testUnmarshallObjectRemovesParent() {
        this.unmarshallRemovesParentCheck(
                JsonNode.object()
                        .setChild(
                                JsonPropertyName.with("abc"),
                                JsonNode.string("def")
                        )
        );
    }

    private void unmarshallRemovesParentCheck(final JsonNode child) {
        this.unmarshallAndCheck(JsonNode.object()
                        .set(JsonPropertyName.with("child"), child)
                        .children().get(0),
                child.removeParent());
    }

    @Override
    BasicJsonMarshallerTypedJsonNode marshaller() {
        return BasicJsonMarshallerTypedJsonNode.instance();
    }

    @Override
    JsonNode value() {
        return JsonNode.string("abc123");
    }

    @Override
    JsonNode node() {
        return this.value();
    }

    @Override
    JsonNode jsonNullNode() {
        return JsonNode.nullNode();
    }

    @Override
    String typeName() {
        return "json";
    }

    @Override
    Class<JsonNode> marshallerType() {
        return JsonNode.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedJsonNode> type() {
        return BasicJsonMarshallerTypedJsonNode.class;
    }
}
