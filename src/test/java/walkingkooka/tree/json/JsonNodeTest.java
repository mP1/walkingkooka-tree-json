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

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.HasTextOffsetTesting;

public final class JsonNodeTest implements ClassTesting2<JsonNode>,
        HasTextOffsetTesting,
        ParseStringTesting<JsonNode> {

    // isClass..........................................................................................................

    @Test
    public void testIsClassWithNull() {
        this.isClassAndCheck(
                null,
                false
        );
    }

    @Test
    public void testIsClassWithNonJsonNodeSubClass() {
        this.isClassAndCheck(
                this.getClass(),
                false
        );
    }

    @Test
    public void testIsClassWithJsonNode() {
        this.isClassAndCheck(
                JsonNode.class,
                true
        );
    }

    @Test
    public void testIsClassWithJsonBoolean() {
        this.isClassAndCheck(
                JsonBoolean.class,
                true
        );
    }

    @Test
    public void testIsClassWithJsonNumber() {
        this.isClassAndCheck(
                JsonNumber.class,
                true
        );
    }

    @Test
    public void testIsClassWithJsonString() {
        this.isClassAndCheck(
                JsonString.class,
                true
        );
    }

    @Test
    public void testIsClassWithJsonArray() {
        this.isClassAndCheck(
                JsonArray.class,
                true
        );
    }

    @Test
    public void testIsClassWithJsonObject() {
        this.isClassAndCheck(
                JsonObject.class,
                true
        );
    }

    private void isClassAndCheck(final Class<?> type,
                                 final boolean expected) {
        this.checkEquals(
                expected,
                JsonNode.isClass(type)
        );
    }

    // parse............................................................................................................

    @Test
    public void testKeyMissingValueFails() {
        this.parseStringFails("\"a1\":", IllegalArgumentException.class);
    }

    @Test
    public void testKeyValuePairFails() {
        this.parseStringFails("\"a1\": \"b2\"", IllegalArgumentException.class);
    }

    @Test
    public void testKeyValuePairTwiceFails() {
        this.parseStringFails("\"a1\": \"b2\", \"c3\": \"d4\"", IllegalArgumentException.class);
    }

    @Test
    public void testParseIncompleteObjectFails() {
        this.parseStringFails("{\"", IllegalArgumentException.class);
    }

    @Test
    public void testParseIncompleteArrayFails() {
        this.parseStringFails("[1,", IllegalArgumentException.class);
    }

    @Test
    public void testParseIncompleteArrayFails2() {
        this.parseStringFails("[1,", IllegalArgumentException.class);
    }

    @Test
    public void testParseBoolean() {
        this.parseStringAndCheck("true",
                JsonNode.booleanNode(true));
    }

    @Test
    public void testParseNull() {
        this.parseStringAndCheck("null",
                JsonNode.nullNode());
    }

    @Test
    public void testParseNumber() {
        this.parseStringAndCheck("123.5",
                JsonNode.number(123.5));
    }

    @Test
    public void testParseString() {
        this.parseStringAndCheck("\"abc123\"",
                JsonNode.string("abc123"));
    }

    @Test
    public void testParseArray() {
        this.parseStringAndCheck("[\"abc123\", true]",
                JsonNode.array()
                        .appendChild(JsonNode.string("abc123"))
                        .appendChild(JsonNode.booleanNode(true)));
    }

    @Test
    public void testParseObject() {
        this.parseStringAndCheck("{\"prop1\": \"value1\"}",
                JsonNode.object().set(JsonPropertyName.with("prop1"), JsonNode.string("value1")));
    }

    @Test
    public void testParseObject2() {
        this.parseStringAndCheck("{ \"prop1\": \"value1\" }",
                JsonNode.object().set(JsonPropertyName.with("prop1"), JsonNode.string("value1")));
    }

    @Test
    public void testParseObjectManyProperties() {
        this.parseStringAndCheck("{\"prop1\": \"value1\", \"prop2\": \"value2\"}",
                JsonNode.object()
                        .set(JsonPropertyName.with("prop1"), JsonNode.string("value1"))
                        .set(JsonPropertyName.with("prop2"), JsonNode.string("value2")));
    }

    @Test
    public void testParseObjectOrderUnimportant() {
        this.parseStringAndCheck("{\"prop1\": \"value1\", \"prop2\": \"value2\"}",
                JsonNode.object()
                        .set(JsonPropertyName.with("prop2"), JsonNode.string("value2"))
                        .set(JsonPropertyName.with("prop1"), JsonNode.string("value1")));
    }

    // HasTextOffset.................................................................................................

    @Test
    public void testHasTextOffsetEmptyJsonObject() {
        this.textOffsetAndCheck(JsonObject.object(), 0);
    }

    @Test
    public void testHasTextOffsetJsonObject() {
        this.textOffsetAndCheck(JsonObject.object()
                        .set(JsonPropertyName.with("key1"), JsonNode.string("a1"))
                        .set(JsonPropertyName.with("key2"), JsonNode.string("b2"))
                        .set(JsonPropertyName.with("key3"), JsonNode.string("c3"))
                        .set(JsonPropertyName.with("key4"), JsonNode.string("d4"))
                        .children()
                        .get(2),
                "a1b2");
    }

    @Test
    public void testHasTextOffsetJsonArray() {
        this.textOffsetAndCheck(JsonObject.array()
                        .appendChild(JsonNode.string("a1"))
                        .appendChild(JsonObject.array()
                                .appendChild(JsonNode.string("b2"))
                                .appendChild(JsonNode.string("c3"))
                                .appendChild(JsonNode.string("d4")))
                        .appendChild(JsonNode.string("e5"))
                        .get(1)
                        .children()
                        .get(2),
                "a1b2c3");
    }

    // ClassTesting.............................................................................................

    @Override
    public Class<JsonNode> type() {
        return JsonNode.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // ParseStringTesting ........................................................................................

    @Override
    public JsonNode parseString(final String text) {
        return JsonNode.parse(text);
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        this.checkEquals(expected instanceof IllegalArgumentException, expected + " is not a sub class of " + IllegalArgumentException.class);
        return expected;
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return IllegalArgumentException.class;
    }
}
