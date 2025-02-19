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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.UnsupportedTypeJsonNodeException;

import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonNodeMarshallContextTest extends BasicJsonNodeContextTestCase<BasicJsonNodeMarshallContext>
    implements JsonNodeMarshallContextTesting<BasicJsonNodeMarshallContext> {

    // marshall.....................................................................................................

    @Test
    public void testMarshallUnknownTypeFails() {
        assertThrows(
            UnsupportedTypeJsonNodeException.class,
            () -> this.createContext().marshall(this)
        );
    }

    @Test
    public void testMarshallBooleanTrue() {
        this.marshallAndCheck(true,
            JsonNode.booleanNode(true));
    }

    @Test
    public void testMarshallNumber() {
        final double value = 1.25;
        this.marshallAndCheck(value,
            JsonNode.number(value));
    }

    @Test
    public void testMarshallString() {
        final String value = "abc123";
        this.marshallAndCheck(value,
            JsonNode.string(value));
    }

    @Test
    public void testMarshallWithObjectPostProcessor() {
        final TestJsonNodeValue value = TestJsonNodeValue.with("abc123");
        this.marshallAndCheck(this.contextWithProcessor(),
            value,
            value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE));
    }

    // marshallCollection...................................................................................................

    @Test
    public void testMarshallCollectionNullList() {
        this.marshallCollectionAndCheck(null,
            JsonNode.nullNode());
    }

    @Test
    public void testMarshallCollectionBooleanTrue() {
        this.marshallCollectionAndCheck(Lists.of(true),
            list(JsonNode.booleanNode(true)));
    }

    @Test
    public void testMarshallCollectionBooleanFalse() {
        this.marshallCollectionAndCheck(Lists.of(false),
            list(JsonNode.booleanNode(false)));
    }

    @Test
    @Override
    public void testMarshallCollectionNull() {
        this.marshallCollectionAndCheck(Lists.of((Object) null),
            list(JsonNode.nullNode()));
    }

    @Test
    public void testMarshallCollectionNumber() {
        final double value = 1.25;
        this.marshallCollectionAndCheck(Lists.of(value),
            list(JsonNode.number(value)));
    }

    @Test
    public void testMarshallCollectionString() {
        final String value = "abc123";
        this.marshallCollectionAndCheck(Lists.of(value),
            list(JsonNode.string(value)));
    }

    @Test
    public void testMarshallCollectionWithObjectPostProcessor() {
        final TestJsonNodeValue value = TestJsonNodeValue.with("abc123");
        this.marshallCollectionAndCheck(this.contextWithProcessor(),
            Lists.of(value),
            list(value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE)));
    }

    private JsonArray list(final JsonNode element) {
        return JsonNode.array().appendChild(element);
    }

    // marshallEnumSet..................................................................................................

    @Test
    public void testMarshallEnumSetWithNull() {
        this.marshallEnumSetAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshallEnumSetWithNonNull() {
        this.marshallEnumSetAndCheck(
            EnumSet.of(RoundingMode.CEILING, RoundingMode.FLOOR),
            JsonNode.string("CEILING,FLOOR")
        );
    }

    // marshallMap....................................................................................................

    @Test
    public void testMarshallMapNullMap() {
        this.marshallMapAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshallMapEmptyMap() {
        this.marshallMapAndCheck(
            Maps.empty(),
            JsonNode.object()
        );
    }

    @Test
    public void testMarshallMapStringBooleanTrue() {
        this.marshallMapStringKeyAndCheck2(
            true,
            JsonNode.booleanNode(true)
        );
    }

    @Test
    public void testMarshallMapStringBooleanFalse() {
        this.marshallMapStringKeyAndCheck2(
            false,
            JsonNode.booleanNode(false)
        );
    }

    @Test
    public void testMarshallMapStringNullValue() {
        this.marshallMapStringKeyAndCheck2(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshallMapStringNumber() {
        final double value = 1.25;
        this.marshallMapStringKeyAndCheck2(
            value,
            JsonNode.number(value)
        );
    }

    @Test
    public void testMarshallMapStringString() {
        final String value = "abc123";
        this.marshallMapStringKeyAndCheck2(
            value,
            JsonNode.string(value)
        );
    }

    private <VV> void marshallMapStringKeyAndCheck2(final VV value,
                                                    final JsonNode expected) {
        final String key = "key1";

        this.marshallMapAndCheck(
            Maps.of(key, value),
            JsonNode.object()
                .set(JsonPropertyName.with(key), expected)
        );
    }

    @Test
    public void testMarshallMapStringKeyWithObjectPostProcessor() {
        final String key = "key-123";
        final TestJsonNodeValue value = TestJsonNodeValue.with(key);

        this.marshallMapAndCheck(
            this.contextWithProcessor(),
            Maps.of(key, value),
            JsonNode.object()
                .set(
                    JsonPropertyName.with(key),
                    value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE)
                )
        );
    }

    @Test
    public void testMarshallMapNonStringKeyBooleanTrue() {
        this.marshallMapNonStringKeyAndCheck2(
            true,
            JsonNode.booleanNode(true)
        );
    }

    @Test
    public void testMarshallMapNonStringKeyBooleanFalse() {
        this.marshallMapNonStringKeyAndCheck2(
            false,
            JsonNode.booleanNode(false)
        );
    }

    @Test
    public void testMarshallMapNonStringKeyNullValue() {
        this.marshallMapNonStringKeyAndCheck2(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public void testMarshallMapNonStringKeyNumber() {
        final double value = 1.25;
        this.marshallMapNonStringKeyAndCheck2(value,
            JsonNode.number(value)
        );
    }

    @Test
    public void testMarshallMapNonStringKeyString() {
        final String value = "abc123";
        this.marshallMapNonStringKeyAndCheck2(
            value,
            JsonNode.string(value)
        );
    }

    private <VV> void marshallMapNonStringKeyAndCheck2(final VV value,
                                                       final JsonNode expected) {
        final Integer KEY = 123;

        this.marshallMapAndCheck(
            Maps.of(KEY, value),
            keyValueEntry(KEY, expected)
        );
    }

    @Test
    public void testMarshallMapNonStringKeyWithObjectPostProcessor() {
        final Integer key = 123;
        final TestJsonNodeValue value = TestJsonNodeValue.with("" + key);

        this.marshallMapAndCheck(
            this.contextWithProcessor(),
            Maps.of(key, value),
            this.keyValueEntry(key, value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE))
        );
    }

    private JsonNode keyValueEntry(final Integer key, final JsonNode value) {
        return JsonNode.array()
            .appendChild(
                JsonNode.object()
                    .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, JsonNode.number(key))
                    .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, value)
            );
    }

    // marshallWithType...............................................................................................

    @Test
    public void testMarshallWithTypeBooleanTrue() {
        this.marshallWithTypeAndCheck(true, JsonNode.booleanNode(true));
    }

    @Test
    public void testMarshallWithTypeBooleanFalse() {
        this.marshallWithTypeAndCheck(false, JsonNode.booleanNode(false));
    }

    @Test
    public void testMarshallWithTypeNumberByte() {
        final byte value = 123;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("byte", JsonNode.number(value)));
    }

    @Test
    public void testMarshallWithTypeNumberShort() {
        final short value = 123;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("short", JsonNode.number(value)));
    }

    @Test
    public void testMarshallWithTypeNumberInteger() {
        final int value = 123;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("int", JsonNode.number(value)));
    }

    @Test
    public void testMarshallWithTypeNumberLong() {
        final long value = 123;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("long", JsonNode.string(String.valueOf(value))));
    }

    @Test
    public void testMarshallWithTypeNumberFloat() {
        final float value = 123.5f;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("float", JsonNode.number(value)));
    }

    @Test
    public void testMarshallWithTypeNumberDouble() {
        final double value = 1.25;
        this.marshallWithTypeAndCheck(value, JsonNode.number(value));
    }

    @Test
    public void testMarshallWithTypeObject() {
        final Locale value = Locale.ENGLISH;
        this.marshallWithTypeAndCheck(value,
            this.typeAndValue("locale", JsonNode.string(value.toLanguageTag())));
    }

    @Test
    public void testMarshallWithTypeString() {
        final String value = "abc123";
        this.marshallWithTypeAndCheck(value, JsonNode.string(value));
    }

    @Test
    public void testMarshallWithTypeObjectPostProcessor() {
        final String string = "abc123";
        final TestJsonNodeValue value = TestJsonNodeValue.with(string);
        this.marshallWithTypeAndCheck(this.contextWithProcessor(),
            value,
            this.typeAndValue(TestJsonNodeValue.TYPE_NAME, value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE)));
    }

    // marshallCollectionWithType.......................................................................................

    @Test
    public void testMarshallCollectionWithTypeNullList() {
        this.marshallCollectionWithTypeAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public void testMarshallCollectionWithTypeBooleanTrue() {
        this.marshallCollectionWithTypeAndCheck(Lists.of(true),
            this.list(JsonNode.booleanNode(true)));
    }

    @Test
    public void testMarshallCollectionWithTypeBooleanFalse() {
        this.marshallCollectionWithTypeAndCheck(Lists.of(false),
            this.list(JsonNode.booleanNode(false)));
    }

    @Test
    public void testMarshallCollectionWithTypeNullElement() {
        this.marshallCollectionWithTypeAndCheck(Lists.of((Object) null),
            this.list(JsonNode.nullNode()));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberByte() {
        final byte value = 1;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("byte", JsonNode.number(value))));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberShort() {
        final short value = 1;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("short", JsonNode.number(value))));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberInteger() {
        final int value = 123;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("int", JsonNode.number(value))));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberLong() {
        final long value = 123;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("long", JsonNode.string(String.valueOf(value)))));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberFloat() {
        final float value = 1.25f;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("float", JsonNode.number(value))));
    }

    @Test
    public void testMarshallCollectionWithTypeNumberDouble() {
        final double value = 1.25;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(JsonNode.number(value)));
    }

    @Test
    public void testMarshallCollectionWithTypeObject() {
        final Locale value = Locale.ENGLISH;
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(this.typeAndValue("locale", JsonNode.string(value.toLanguageTag()))));
    }

    @Test
    public void testMarshallCollectionWithTypeString() {
        final String value = "abc123";
        this.marshallCollectionWithTypeAndCheck(Lists.of(value),
            this.list(JsonNode.string(value)));
    }

    @Test
    public void testMarshallCollectionWithTypeWithObjectPostProcessorWithType() {
        final String string = "abc123";
        final TestJsonNodeValue value = TestJsonNodeValue.with(string);

        this.marshallCollectionWithTypeAndCheck(this.contextWithProcessor(),
            Lists.of(value),
            this.list(this.typeAndValue(TestJsonNodeValue.TYPE_NAME, value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE))));
    }

    // marshallMapWithType..............................................................................................

    @Test
    public void testMarshallMapWithTypeNullMap() {
        this.marshallMapWithTypeAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public void testMarshallMapWithTypeBooleanTrue() {
        this.marshallMapWithTypeAndCheck3(JsonNode.booleanNode(true),
            true);
    }

    @Test
    public void testMarshallMapWithTypeBooleanFalse() {
        this.marshallMapWithTypeAndCheck3(JsonNode.booleanNode(false),
            false);
    }

    @Test
    public void testMarshallMapWithTypeNullValue() {
        this.marshallMapWithTypeAndCheck3(JsonNode.nullNode(),
            null);
    }

    @Test
    public void testMarshallMapWithTypeNumber() {
        final double value = 1.25;
        this.marshallMapWithTypeAndCheck3(JsonNode.number(value),
            value);
    }

    @Test
    public void testMarshallMapWithTypeString() {
        final String value = "abc123";
        this.marshallMapWithTypeAndCheck3(JsonNode.string(value),
            value);
    }

    private <VV> void marshallMapWithTypeAndCheck3(final JsonNode value,
                                                   final VV javaValue) {
        final String KEY = "key1";

        this.marshallMapWithTypeAndCheck(Maps.of(KEY, javaValue),
            JsonNode.array()
                .appendChild(JsonNode.object()
                    .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, JsonNode.string(KEY))
                    .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, value)));
    }

    @Test
    public void testMarshallMapWithTypeWithObjectPostProcessor() {
        final String key = "abc123";
        final TestJsonNodeValue value = TestJsonNodeValue.with(key);

        this.marshallMapWithTypeAndCheck(this.contextWithProcessor(),
            Maps.of(key, value),
            JsonNode.array()
                .appendChild(JsonNode.object()
                    .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, JsonNode.string(key))
                    .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, this.typeAndValue(TestJsonNodeValue.TYPE_NAME, value.marshall(JsonNodeMarshallContexts.fake()).set(POST, POST_VALUE)))));
    }

    // JsonNodeMarshallContext................................................................................................

    @Override
    public BasicJsonNodeMarshallContext createContext() {
        return BasicJsonNodeMarshallContext.INSTANCE;
    }

    private JsonNodeMarshallContext contextWithProcessor() {
        return this.createContext().setObjectPostProcessor(this::objectPostProcessor);
    }

    private JsonObject objectPostProcessor(final Object value, JsonObject object) {
        return object.set(POST, POST_VALUE);
    }

    private final static JsonPropertyName POST = JsonPropertyName.with("post");
    private final static JsonNode POST_VALUE = JsonNode.booleanNode(true);

    @Override
    public Class<BasicJsonNodeMarshallContext> type() {
        return BasicJsonNodeMarshallContext.class;
    }
}
