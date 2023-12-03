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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;

import java.util.List;

public final class BasicJsonMarshallerTypedCollectionListTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedCollectionList, List<?>> {

    @BeforeAll
    public static void beforeAll() {
        TestJsonNodeValue.register();
    }

    @AfterAll
    public static void afterAll() {
        TestJsonNodeValue.unregister();
    }

    @Test
    public void testUnmarshallBooleanFails() {
        this.unmarshallFailed(
                JsonNode.booleanNode(true),
                JsonNodeUnmarshallException.class
        );
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(
                JsonNode.number(123),
                JsonNodeUnmarshallException.class
        );
    }

    @Test
    public void testUnmarshallStringFails() {
        this.unmarshallFailed(
                JsonNode.string("abc123"),
                JsonNodeUnmarshallException.class
        );
    }

    @Test
    public void testUnmarshallEmptyArray() {
        this.unmarshallAndCheck(JsonNode.array(), Lists.empty());
    }

    @Test
    public void testMarshallEmptyList() {
        this.marshallWithTypeAndCheck(Lists.empty(), this.typeAndValue(JsonNode.array()));
    }

    @Override
    BasicJsonMarshallerTypedCollectionList marshaller() {
        return BasicJsonMarshallerTypedCollectionList.instance();
    }

    @Override
    List<?> value() {
        return Lists.of(null, true, 123.5, "abc123", TestJsonNodeValue.with("test-has-json-node"));
    }

    @Override
    JsonNode node() {
        return JsonNode.array()
                .appendChild(JsonNode.nullNode())
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.number(123.5))
                .appendChild(JsonNode.string("abc123"))
                .appendChild(this.marshallContext().marshallWithType(TestJsonNodeValue.with("test-has-json-node")));
    }

    @Override
    List<?> jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    Class<List<?>> marshallerType() {
        return Cast.to(List.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedCollectionList> type() {
        return BasicJsonMarshallerTypedCollectionList.class;
    }
}
