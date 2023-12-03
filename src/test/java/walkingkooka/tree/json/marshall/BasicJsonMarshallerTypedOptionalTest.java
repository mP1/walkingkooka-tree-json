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
import walkingkooka.tree.json.JsonPropertyName;

import java.math.BigDecimal;
import java.util.Optional;

public final class BasicJsonMarshallerTypedOptionalTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedOptional, Optional<?>> {

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
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testUnmarshallNumberFails() {
        this.unmarshallFailed(JsonNode.number(123), ClassCastException.class);
    }

    @Test
    public void testUnmarshallStringFails() {
        this.unmarshallFailed(JsonNode.string("a1"), ClassCastException.class);
    }

    @Test
    public void testUnmarshallObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @Test
    public void testUnmarshallArrayTwoElementsFails() {
        this.unmarshallFailed(
                JsonNode.array()
                        .appendChild(JsonNode.number(1))
                        .appendChild(JsonNode.number(2)),
                JsonNodeUnmarshallException.class
        );
    }

    @Test
    public void testUnmarshallEmptyArray() {
        this.unmarshallAndCheck(JsonNode.array(), Optional.empty());
    }

    @Test
    public void testUnmarshallArrayWithBooleanTrue() {
        this.unmarshallAndCheck(JsonNode.array().appendChild(JsonNode.booleanNode(true)),
                Optional.of(true));
    }

    @Test
    public void testUnmarshallArrayWithBooleanFalse() {
        this.unmarshallAndCheck(JsonNode.array().appendChild(JsonNode.booleanNode(false)),
                Optional.of(false));
    }

    @Test
    public void testUnmarshallArrayWithNumber() {
        this.unmarshallAndCheck(JsonNode.array().appendChild(JsonNode.number(1.5)),
                Optional.of(1.5));
    }

    @Test
    public void testUnmarshallArrayWithString() {
        this.unmarshallAndCheck(JsonNode.array().appendChild(JsonNode.string("abc123")),
                Optional.of("abc123"));
    }

    @Test
    public void testMarshallEmptyOptional() {
        this.marshallAndCheck(Optional.empty(), JsonNode.array());
    }

    @Test
    public void testMarshallBooleanTrue() {
        this.marshallAndCheck2(true, JsonNode.booleanNode(true));
    }

    @Test
    public void testMarshallBooleanFalse() {
        this.marshallAndCheck2(false, JsonNode.booleanNode(false));
    }

    @Test
    public void testMarshallDouble() {
        this.marshallAndCheck2(1.5, JsonNode.number(1.5));
    }

    @Test
    public void testMarshallString() {
        this.marshallAndCheck2("abc123", JsonNode.string("abc123"));
    }

    private void marshallAndCheck2(final Object value, final JsonNode arrayElement) {
        this.marshallAndCheck(Optional.of(value),
                JsonNode.array().appendChild(arrayElement));
    }

    @Test
    public void testRoundtripEmpty() {
        this.roundtripAndCheck(Optional.empty());
    }

    @Test
    public void testRoundtripBooleanTrue() {
        this.roundtripAndCheck(Optional.of(true));
    }

    @Test
    public void testRoundtripBooleanFalse() {
        this.roundtripAndCheck(Optional.of(false));
    }

    @Test
    public void testRoundtripNumber() {
        this.roundtripAndCheck(Optional.of(2.5));
    }

    @Test
    public void testRoundtripString() {
        this.roundtripAndCheck(Optional.of("a1"));
    }

    @Test
    public void testRoundtripBigDecimal() {
        this.roundtripAndCheck(Optional.of(BigDecimal.valueOf(123)));
    }

    @Test
    public void testRoundtripJsonObjectWithProperties() {
        this.roundtripAndCheck(Optional.of(JsonNode.object()
                .set(JsonPropertyName.with("country"), JsonNode.string("australia"))
                .set(JsonPropertyName.with("year"), JsonNode.number(2019))
        ));
    }

    @Test
    public void testRoundtripListBooleanNumberStringBigDecimal() {
        this.roundtripAndCheck(Optional.of(Lists.of(true, 1.0, "a1", BigDecimal.valueOf(123))));
    }

    @Test
    public void testRoundtripTestJsonNodeMap() {
        this.roundtripAndCheck(Optional.of(TestJsonNodeValue.with("Test123")));
    }

    private void roundtripAndCheck(final Optional<?> value) {
        final BasicJsonMarshallerTypedOptional marshaller = this.marshaller();
        final JsonNode json = marshaller.marshall(value, this.marshallContext());
        final Optional<?> from = marshaller.unmarshall(json, this.unmarshallContext());

        this.checkEquals(value, from, () -> "json\n" + json);

        final JsonNode jsonWithType = this.marshallContext()
                .marshallWithType(value);
        final Optional<?> from2 = this.unmarshallContext()
                .unmarshallWithType(jsonWithType);

        this.checkEquals(value, from2, () -> "jsonWithType\n" + jsonWithType);
    }

    @Override
    BasicJsonMarshallerTypedOptional marshaller() {
        return BasicJsonMarshallerTypedOptional.instance();
    }

    @Override
    Optional<?> value() {
        return Optional.of(JAVA_VALUE);
    }

    @Override
    JsonNode node() {
        return JsonNode.array().appendChild(JsonNode.string(JAVA_VALUE));
    }

    private final static String JAVA_VALUE = "abc123";

    @SuppressWarnings("OptionalAssignedToNull")
    @Override
    Optional<?> jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "optional";
    }

    @Override
    Class<Optional<?>> marshallerType() {
        return Cast.to(Optional.class);
    }

    @Override
    public Class<BasicJsonMarshallerTypedOptional> type() {
        return BasicJsonMarshallerTypedOptional.class;
    }
}
