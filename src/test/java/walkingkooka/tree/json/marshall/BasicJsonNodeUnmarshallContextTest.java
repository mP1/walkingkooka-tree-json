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
import walkingkooka.collect.set.Sets;
import walkingkooka.currency.CanCurrencyForCurrencyCode;
import walkingkooka.locale.CanLocaleForLanguageTag;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.UnsupportedTypeJsonNodeException;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonNodeUnmarshallContextTest extends BasicJsonNodeContextTestCase<BasicJsonNodeUnmarshallContext>
    implements JsonNodeUnmarshallContextTesting<BasicJsonNodeUnmarshallContext> {

    private final CanCurrencyForCurrencyCode CAN_CURRENCY_FOR_CURRENCY_CODE = (String cc) -> Optional.ofNullable(
        Currency.getInstance(cc)
    );

    private final CanLocaleForLanguageTag CAN_LOCALE_FOR_LANGUAGE_TAG = (String lt) -> Optional.of(
        Locale.forLanguageTag(lt)
    );
    
    @Test
    public void testWithNullCanCurrencyForCurrencyCodeFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeUnmarshallContext.with(
                null,
                CAN_LOCALE_FOR_LANGUAGE_TAG,
                ExpressionNumberKind.BIG_DECIMAL,
                MathContext.DECIMAL32
            )
        );
    }

    @Test
    public void testWithNullCanLocaleForLanguageTagFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeUnmarshallContext.with(
                CAN_CURRENCY_FOR_CURRENCY_CODE,
                null,
                ExpressionNumberKind.BIG_DECIMAL,
                MathContext.DECIMAL32
            )
        );
    }

    @Test
    public void testWithNullKindFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeUnmarshallContext.with(
                CAN_CURRENCY_FOR_CURRENCY_CODE,
                CAN_LOCALE_FOR_LANGUAGE_TAG,
                null,
                MathContext.DECIMAL32
            )
        );
    }

    @Test
    public void testWithNullContextFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> BasicJsonNodeUnmarshallContext.with(
                CAN_CURRENCY_FOR_CURRENCY_CODE,
                CAN_LOCALE_FOR_LANGUAGE_TAG,
                ExpressionNumberKind.DEFAULT,
                null
            )
        );
    }

    // unmarshall.....................................................................................................

    @Test
    public void testUnmarshallUnknownTypeFails() {
        final UnsupportedTypeJsonNodeException thrown = assertThrows(
            UnsupportedTypeJsonNodeException.class,
            () -> this.createContext()
                .unmarshall(
                    JsonNode.nullNode(),
                    this.getClass()
                )
        );
        this.checkEquals(
            "Missing json unmarshaller for type \"walkingkooka.tree.json.marshall.BasicJsonNodeUnmarshallContextTest\"",
            thrown.getMessage()
        );
    }

    @Test
    public void testUnmarshallBooleanTrue() {
        this.unmarshallAndCheck(JsonNode.booleanNode(true),
            Boolean.class,
            true);
    }

    @Test
    public void testUnmarshallBooleanFalse() {
        this.unmarshallAndCheck(JsonNode.booleanNode(false),
            Boolean.class,
            false);
    }

    @Test
    public void testUnmarshallNull() {
        this.unmarshallAndCheck(JsonNode.nullNode(),
            Boolean.class,
            null);
    }

    @Test
    public void testUnmarshallNumber() {
        final double value = 1.25;
        this.unmarshallAndCheck(JsonNode.number(value),
            Double.class,
            value);
    }

    @Test
    public void testUnmarshallString() {
        final String value = "abc123";
        this.unmarshallAndCheck(JsonNode.string(value),
            String.class,
            value);
    }

    @Test
    public void testUnmarshallObject() {
        this.unmarshallAndCheck(this.jsonNode(),
            TestJsonNodeValue.class,
            this.value());
    }

    @Test
    public void testUnmarshallObjectWithObjectPreProcessor() {
        this.unmarshallAndCheck(this.contextWithPreProcessor(),
            this.jsonNode2(),
            TestJsonNodeValue.class,
            this.value());
    }

    // unmarshallEnumSet................................................................................................

    @Test
    public void testUnmarshallEnumSetNull() {
        this.unmarshallEnumSetAndCheck(
            JsonNode.nullNode(),
            RoundingMode.class,
            null
        );
    }

    @Test
    public void testUnmarshallEnumSetEmptyString() {
        this.unmarshallEnumSetAndCheck(
            JsonNode.string(""),
            RoundingMode.class,
            Sets.empty()
        );
    }

    @Test
    public void testUnmarshallEnumSetSeveralValues() {
        this.unmarshallEnumSetAndCheck(
            JsonNode.string("CEILING,FLOOR"),
            RoundingMode.class,
            EnumSet.of(RoundingMode.CEILING, RoundingMode.FLOOR)
        );
    }

    // unmarshallOptional...............................................................................................

    @Test
    public void testUnmarshallOptional() {
        this.unmarshallOptionalAndCheck(
            JsonNode.nullNode(),
            RoundingMode.class,
            Optional.empty()
        );
    }

    @Test
    public void testUnmarshallOptionalEmptyString() {
        this.unmarshallOptionalAndCheck(
            JsonNode.string(""),
            String.class,
            Optional.of("")
        );
    }

    @Test
    public void testUnmarshallOptionalNotEmpty() {
        this.unmarshallOptionalAndCheck(
            JsonNode.string("CEILING"),
            RoundingMode.class,
            Optional.of(
                RoundingMode.CEILING
            )
        );
    }

    @Test
    public void testRoundtripOptionalEmpty() {
        final Optional<?> optional = Optional.empty();

        this.checkEquals(
            optional,
            this.createContext()
                .unmarshallOptional(
                    BasicJsonNodeMarshallContext.INSTANCE.marshallOptional(optional),
                    String.class
                )
        );
    }

    @Test
    public void testRoundtripOptionalNotEmpty() {
        final Optional<?> optional = Optional.of("Hello");

        this.checkEquals(
            optional,
            this.createContext()
                .unmarshallOptional(
                    BasicJsonNodeMarshallContext.INSTANCE.marshallOptional(optional),
                    String.class
                )
        );
    }

    // unmarshallOptionalWithType.......................................................................................

    @Test
    public void testUnmarshallOptionalWithType() {
        this.unmarshallOptionalWithTypeAndCheck(
            JsonNode.nullNode(),
            Optional.empty()
        );
    }

    @Test
    public void testUnmarshallOptionalWithTypeEmptyString() {
        this.unmarshallOptionalWithTypeAndCheck(
            JsonNode.string(""),
            Optional.of("")
        );
    }

    @Test
    public void testUnmarshallOptionalWithTypeNotEmpty() {
        this.unmarshallOptionalWithTypeAndCheck(
            JsonNode.object()
                .set(
                    JsonPropertyName.with("type"),
                    JsonNode.string("rounding-mode")
                ).set(
                    JsonPropertyName.with("value"),
                    JsonNode.string("CEILING")
                ),
            Optional.of(
                RoundingMode.CEILING
            )
        );
    }

    @Test
    public void testRoundtripOptionalWithTypeEmpty() {
        final Optional<?> optional = Optional.empty();

        this.checkEquals(
            optional,
            this.createContext()
                .unmarshallOptionalWithType(
                    BasicJsonNodeMarshallContext.INSTANCE.marshallOptionalWithType(optional)
                )
        );
    }

    @Test
    public void testRoundtripOptionalWithTypeNotEmpty() {
        final Optional<?> optional = Optional.of("Hello");

        this.checkEquals(
            optional,
            this.createContext()
                .unmarshallOptionalWithType(
                    BasicJsonNodeMarshallContext.INSTANCE.marshallOptionalWithType(optional)
                )
        );
    }

    // unmarshallList.................................................................................................

    @Test
    public void testUnmarshallListBooleanTrue() {
        this.unmarshallListAndCheck(list(JsonNode.booleanNode(true)),
            Boolean.class,
            Lists.of(true));
    }

    @Test
    public void testUnmarshallListBooleanFalse() {
        this.unmarshallListAndCheck(list(JsonNode.booleanNode(false)),
            Boolean.class,
            Lists.of(false));
    }

    @Test
    public void testUnmarshallListNull() {
        this.unmarshallListAndCheck(list(JsonNode.nullNode()),
            Boolean.class,
            Lists.of((Boolean) null));
    }

    @Test
    public void testUnmarshallListNumber() {
        final double value = 1.25;
        this.unmarshallListAndCheck(list(JsonNode.number(value)),
            Double.class,
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListString() {
        final String value = "abc123";
        this.unmarshallListAndCheck(list(JsonNode.string(value)),
            String.class,
            Lists.of(value));
    }

    @Test
    public void testUnmarshallList() {
        this.unmarshallListAndCheck(this.list(this.jsonNode()),
            TestJsonNodeValue.class,
            Lists.of(this.value()));
    }

    @Test
    public void testUnmarshallListObjectWithObjectPreProcessor() {
        this.unmarshallListAndCheck(this.contextWithPreProcessor(),
            this.list(this.jsonNode2()),
            TestJsonNodeValue.class,
            Lists.of(this.value()));
    }

    private JsonArray list(final JsonNode element) {
        return JsonNode.array().appendChild(element);
    }

    // unmarshallSet..................................................................................................

    @Test
    public void testUnmarshallSetBooleanTrue() {
        this.unmarshallSetAndCheck(set(JsonNode.booleanNode(true)),
            Boolean.class,
            Sets.of(true));
    }

    @Test
    public void testUnmarshallSetBooleanFalse() {
        this.unmarshallSetAndCheck(set(JsonNode.booleanNode(false)),
            Boolean.class,
            Sets.of(false));
    }

    @Test
    public void testUnmarshallSetNull() {
        this.unmarshallSetAndCheck(set(JsonNode.nullNode()),
            Boolean.class,
            Sets.of((Boolean) null));
    }

    @Test
    public void testUnmarshallSetNumber() {
        final double value = 1.25;
        this.unmarshallSetAndCheck(set(JsonNode.number(value)),
            Double.class,
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetString() {
        final String value = "abc123";
        this.unmarshallSetAndCheck(set(JsonNode.string(value)),
            String.class,
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetObject() {
        this.unmarshallSetAndCheck(this.set(this.jsonNode()),
            TestJsonNodeValue.class,
            Sets.of(this.value()));
    }

    @Test
    public void testUnmarshallSetObjectWithObjectPreProcessor() {
        this.unmarshallSetAndCheck(this.contextWithPreProcessor(),
            this.set(this.jsonNode2()),
            TestJsonNodeValue.class,
            Sets.of(this.value()));
    }

    private JsonArray set(final JsonNode element) {
        return JsonNode.array().appendChild(element);
    }

    // unmarshallMap....................................................................................................

    @Test
    public void testUnmarshallMapStringKeyBooleanTrue() {
        this.unmarshallMapStringKeyAndCheck(
            JsonNode.booleanNode(true),
            Boolean.class,
            true
        );
    }

    @Test
    public void testUnmarshallMapStringKeyBooleanFalse() {
        this.unmarshallMapStringKeyAndCheck(
            JsonNode.booleanNode(false),
            Boolean.class,
            false
        );
    }

    @Test
    public void testUnmarshallMapStringKeyNull() {
        this.unmarshallMapStringKeyAndCheck(
            JsonNode.nullNode(),
            Boolean.class,
            null
        );
    }

    @Test
    public void testUnmarshallMapStringKeyNumber() {
        final double value = 1.25;
        this.unmarshallMapStringKeyAndCheck(
            JsonNode.number(value),
            Double.class,
            value
        );
    }

    @Test
    public void testUnmarshallMapStringKeyString() {
        final String value = "abc123";
        this.unmarshallMapStringKeyAndCheck(
            JsonNode.string(value),
            String.class,
            value
        );
    }

    @Test
    public void testUnmarshallMapStringKeyObject() {
        this.unmarshallMapStringKeyAndCheck(
            this.jsonNode(),
            TestJsonNodeValue.class,
            this.value()
        );
    }

    @Test
    public void testUnmarshallMapStringKeyObjectWithObjectPreProcessor() {
        this.unmarshallMapStringKeyAndCheck(
            this.contextWithPreProcessor(),
            this.jsonNode2(),
            TestJsonNodeValue.class,
            this.value()
        );
    }

    private <VV> void unmarshallMapStringKeyAndCheck(final JsonNode value,
                                                     final Class<VV> valueType,
                                                     final VV javaValue) {
        this.unmarshallMapStringKeyAndCheck(
            this.createContext(),
            value,
            valueType,
            javaValue
        );
    }

    private <VV> void unmarshallMapStringKeyAndCheck(final JsonNodeUnmarshallContext context,
                                                     final JsonNode value,
                                                     final Class<VV> valueType,
                                                     final VV javaValue) {
        this.unmarshallMapAndCheck(
            context,
            JsonNode.object()
                .set(JsonPropertyName.with(KEY), value),
            String.class,
            valueType,
            Maps.of(KEY, javaValue)
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyBooleanTrue() {
        this.unmarshallMapNonStringKeyAndCheck(
            JsonNode.booleanNode(true),
            Boolean.class,
            true
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyBooleanFalse() {
        this.unmarshallMapNonStringKeyAndCheck(
            JsonNode.booleanNode(false),
            Boolean.class,
            false
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyNull() {
        this.unmarshallMapNonStringKeyAndCheck(
            JsonNode.nullNode(),
            Boolean.class,
            null
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyNumber() {
        final double value = 1.25;
        this.unmarshallMapNonStringKeyAndCheck(
            JsonNode.number(value),
            Double.class,
            value
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyString() {
        final String value = "abc123";
        this.unmarshallMapNonStringKeyAndCheck(
            JsonNode.string(value),
            String.class,
            value
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyObject() {
        this.unmarshallMapNonStringKeyAndCheck(
            this.jsonNode(),
            TestJsonNodeValue.class,
            this.value()
        );
    }

    @Test
    public void testUnmarshallMapNonStringKeyObjectWithObjectPreProcessor() {
        this.unmarshallMapNonStringKeyAndCheck(
            this.contextWithPreProcessor(),
            this.jsonNode2(),
            TestJsonNodeValue.class,
            this.value()
        );
    }

    private <VV> void unmarshallMapNonStringKeyAndCheck(final JsonNode value,
                                                        final Class<VV> valueType,
                                                        final VV javaValue) {
        this.unmarshallMapNonStringKeyAndCheck(
            this.createContext(),
            value,
            valueType,
            javaValue
        );
    }

    private <VV> void unmarshallMapNonStringKeyAndCheck(final JsonNodeUnmarshallContext context,
                                                        final JsonNode value,
                                                        final Class<VV> valueType,
                                                        final VV javaValue) {
        final Integer key = 123;

        this.unmarshallMapAndCheck(
            context,
            JsonNode.array()
                .appendChild(JsonNode.object()
                    .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, JsonNode.number(key))
                    .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, value)),
            Integer.class,
            valueType,
            Maps.of(key, javaValue)
        );
    }

    // unmarshallWithType.............................................................................................

    @Test
    public void testUnmarshallWithTypeBooleanTrue() {
        this.unmarshallWithTypeAndCheck(this.typeAndValue("boolean", JsonNode.booleanNode(true)),
            true);
    }

    @Test
    public void testUnmarshallWithTypeBooleanFalse() {
        this.unmarshallWithTypeAndCheck(this.typeAndValue("boolean", JsonNode.booleanNode(false)),
            false);
    }

    @Test
    public void testUnmarshallWithTypeNull() {
        this.unmarshallWithTypeAndCheck(this.typeAndValue("boolean", JsonNode.nullNode()),
            null);
    }

    @Test
    public void testUnmarshallWithTypeNumberByte() {
        final byte value = 123;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("byte", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeNumberShort() {
        final short value = 123;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("short", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeNumberInteger() {
        final int value = 123;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("int", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeNumberLong() {
        final long value = 123;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("long", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeNumberFloat() {
        final float value = 123.5f;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("float", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeNumberDouble() {
        final double value = 1.25;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("double", JsonNode.number(value)),
            value);
    }

    @Test
    public void testUnmarshallWithTypeObject() {
        final Locale value = Locale.ENGLISH;
        this.unmarshallWithTypeAndCheck(this.typeAndValue("locale", JsonNode.string(value.toLanguageTag())),
            value);
    }

    @Test
    public void testUnmarshallWithTypeString() {
        final String value = "abc123";
        this.unmarshallWithTypeAndCheck(this.typeAndValue("string", JsonNode.string(value)),
            value);
    }

    @Test
    public void testUnmarshallWithUnknownType() {
        final JsonNodeUnmarshallException thrown = assertThrows(
            JsonNodeUnmarshallException.class,
            () -> this.unmarshallWithTypeAndCheck(
                this.typeAndValue(
                    this.getClass().getName(),
                    JsonNode.booleanNode(true)
                ),
                true
            )
        );

        this.checkEquals(
            "Missing json unmarshaller for \"walkingkooka.tree.json.marshall.BasicJsonNodeUnmarshallContextTest\"",
            thrown.getMessage()
        );
    }


    @Test
    public void testUnmarshallWithTypeObjectWithObjectPreProcessor() {
        this.unmarshallWithTypeAndCheck(this.contextWithPreProcessor(),
            this.typeAndValue(TestJsonNodeValue.TYPE_NAME, this.jsonNode2()),
            this.value());
    }

    // unmarshallWithTypeList.........................................................................................

    @Test
    public void testUnmarshallListWithTypeBooleanTrue() {
        this.unmarshallListWithTypeAndCheck(this.listWithType("boolean", JsonNode.booleanNode(true)),
            Lists.of(true));
    }

    @Test
    public void testUnmarshallListWithTypeBooleanFalse() {
        this.unmarshallListWithTypeAndCheck(this.listWithType("boolean", JsonNode.booleanNode(false)),
            Lists.of(false));
    }

    @Test
    public void testUnmarshallListWithTypeNull() {
        this.unmarshallListWithTypeAndCheck(this.listWithType("boolean", JsonNode.nullNode()),
            Lists.of((Object) null));
    }

    @Test
    public void testUnmarshallListWithTypeNumberByte() {
        final byte value = 1;
        this.unmarshallListWithTypeAndCheck(this.listWithType("byte", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeNumberShort() {
        final short value = 1;
        this.unmarshallListWithTypeAndCheck(this.listWithType("short", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeNumberInteger() {
        final int value = 123;
        this.unmarshallListWithTypeAndCheck(this.listWithType("int", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeNumberLong() {
        final long value = 123;
        this.unmarshallListWithTypeAndCheck(this.listWithType("long", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeNumberFloat() {
        final float value = 1.25f;
        this.unmarshallListWithTypeAndCheck(this.listWithType("float", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeNumberDouble() {
        final double value = 1.25;
        this.unmarshallListWithTypeAndCheck(this.listWithType("double", JsonNode.number(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeObject() {
        final Locale value = Locale.ENGLISH;
        this.unmarshallListWithTypeAndCheck(this.listWithType("locale", JsonNode.string(value.toLanguageTag())),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeString() {
        final String value = "abc123";
        this.unmarshallListWithTypeAndCheck(this.listWithType("string", JsonNode.string(value)),
            Lists.of(value));
    }

    @Test
    public void testUnmarshallListWithTypeObjectWithObjectPreProcessorWithType() {
        this.unmarshallListWithTypeAndCheck(this.contextWithPreProcessor(),
            this.listWithType(TestJsonNodeValue.TYPE_NAME, this.jsonNode2()),
            Lists.of(this.value()));
    }

    private JsonNode listWithType(final String typeName,
                                  final JsonNode node) {
        return JsonNode.array().appendChild(this.typeAndValue(typeName, node));
    }

    // unmarshallWithTypeSet.........................................................................................

    @Test
    public void testUnmarshallSetWithTypeBooleanTrue() {
        this.unmarshallSetWithTypeAndCheck(this.setWithType("boolean", JsonNode.booleanNode(true)),
            Sets.of(true));
    }

    @Test
    public void testUnmarshallSetWithTypeBooleanFalse() {
        this.unmarshallSetWithTypeAndCheck(this.setWithType("boolean", JsonNode.booleanNode(false)),
            Sets.of(false));
    }

    @Test
    public void testUnmarshallSetWithTypeNull() {
        this.unmarshallSetWithTypeAndCheck(this.setWithType("boolean", JsonNode.nullNode()),
            Sets.of((Object) null));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberByte() {
        final byte value = 1;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("byte", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberShort() {
        final short value = 1;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("short", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberInteger() {
        final int value = 123;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("int", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberLong() {
        final long value = 123;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("long", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberFloat() {
        final float value = 1.25f;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("float", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeNumberDouble() {
        final double value = 1.25;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("double", JsonNode.number(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeObject() {
        final Locale value = Locale.ENGLISH;
        this.unmarshallSetWithTypeAndCheck(this.setWithType("locale", JsonNode.string(value.toLanguageTag())),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeString() {
        final String value = "abc123";
        this.unmarshallSetWithTypeAndCheck(this.setWithType("string", JsonNode.string(value)),
            Sets.of(value));
    }

    @Test
    public void testUnmarshallSetWithTypeObjectWithObjectPreProcessorWithType() {
        this.unmarshallSetWithTypeAndCheck(this.contextWithPreProcessor(),
            this.setWithType(TestJsonNodeValue.TYPE_NAME, this.jsonNode2()),
            Sets.of(this.value()));
    }

    private JsonNode setWithType(final String typeName,
                                 final JsonNode node) {
        return JsonNode.array().appendChild(this.typeAndValue(typeName, node));
    }

    // unmarshallWithTypeMap...........................................................................................

    @Test
    public void testUnmarshallMapWithTypeBooleanTrue() {
        this.unmarshallMapWithTypeAndCheck3(JsonNode.booleanNode(true),
            true);
    }

    @Test
    public void testUnmarshallMapWithTypeBooleanFalse() {
        this.unmarshallMapWithTypeAndCheck3(JsonNode.booleanNode(false),
            false);
    }

    @Test
    public void testUnmarshallMapWithTypeNull() {
        this.unmarshallMapWithTypeAndCheck3(JsonNode.nullNode(),
            null);
    }

    @Test
    public void testUnmarshallMapWithTypeNumber() {
        final double value = 1.25;
        this.unmarshallMapWithTypeAndCheck3(JsonNode.number(value),
            value);
    }

    @Test
    public void testUnmarshallMapWithTypeString() {
        final String value = "abc123";
        this.unmarshallMapWithTypeAndCheck3(JsonNode.string(value),
            value);
    }

    @Test
    public void testUnmarshallMapWithTypeObject() {
        this.unmarshallMapWithTypeAndCheck3(this.createContext(),
            this.typeAndValue(TestJsonNodeValue.TYPE_NAME, this.jsonNode()),
            this.value());
    }

    @Test
    public void testUnmarshallMapWithTypeStringObjectWithObjectPreProcessorWithType() {
        this.unmarshallMapWithTypeAndCheck3(this.contextWithPreProcessor(),
            this.typeAndValue(TestJsonNodeValue.TYPE_NAME, this.jsonNode2()),
            this.value());
    }

    private <VV> void unmarshallMapWithTypeAndCheck3(final JsonNode jsonValue,
                                                     final VV javaValue) {
        this.unmarshallMapWithTypeAndCheck3(this.createContext(),
            jsonValue,
            javaValue);
    }

    private <VV> void unmarshallMapWithTypeAndCheck3(final JsonNodeUnmarshallContext context,
                                                     final JsonNode jsonValue,
                                                     final VV javaValue) {
        this.unmarshallMapWithTypeAndCheck(context,
            JsonNode.array()
                .appendChild(JsonNode.object()
                    .set(BasicJsonMarshallerTypedMap.ENTRY_KEY, JsonNode.string(KEY))
                    .set(BasicJsonMarshallerTypedMap.ENTRY_VALUE, jsonValue)),
            Maps.of(KEY, javaValue));
    }

    // JsonNodeUnmarshallContext..............................................................................................

    @Override
    public BasicJsonNodeUnmarshallContext createContext() {
        return BasicJsonNodeUnmarshallContext.with(
            CAN_CURRENCY_FOR_CURRENCY_CODE,
            CAN_LOCALE_FOR_LANGUAGE_TAG,
            ExpressionNumberKind.DEFAULT,
            MathContext.DECIMAL32
        );
    }

    private JsonNodeUnmarshallContext contextWithPreProcessor() {
        return this.createContext()
            .setPreProcessor(this::preProcessor);
    }

    /**
     * If the given node is an object remove the {@link #POST} property.
     */
    private JsonNode preProcessor(final JsonNode node,
                                  final Class<?> type) {
        return node.isObject() ?
            node.objectOrFail().remove(POST) :
            node;
    }

    private JsonObject jsonNode() {
        return JsonNode.object()
            .set(TestJsonNodeValue.KEY, JsonNode.string(VALUE));
    }

    private JsonObject jsonNode2() {
        return this.jsonNode()
            .set(POST, POST_VALUE);
    }

    private TestJsonNodeValue value() {
        return TestJsonNodeValue.with(VALUE);
    }

    private final static JsonPropertyName POST = JsonPropertyName.with("post");
    private final static JsonNode POST_VALUE = JsonNode.booleanNode(true);
    private final static String VALUE = "abc123";
    private final static String KEY = "key1";

    @Override
    public Class<BasicJsonNodeUnmarshallContext> type() {
        return BasicJsonNodeUnmarshallContext.class;
    }
}
