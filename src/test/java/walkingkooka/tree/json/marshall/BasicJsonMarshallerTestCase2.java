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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.StringList;
import walkingkooka.collect.list.TsvStringList;
import walkingkooka.collect.set.CsvStringSet;
import walkingkooka.collect.set.TsvStringSet;
import walkingkooka.currency.CurrencyCodeSet;
import walkingkooka.datetime.LocalDateList;
import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.datetime.LocalTimeList;
import walkingkooka.environment.EnvironmentValueNameSet;
import walkingkooka.locale.LocaleLanguageTagSet;
import walkingkooka.math.NumberList;
import walkingkooka.net.header.ETagList;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;
import walkingkooka.tree.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BasicJsonMarshallerTestCase2<M extends BasicJsonMarshaller<T>, T> extends BasicJsonMarshallerTestCase<M>
    implements JsonNodeMarshallUnmarshallContextTesting,
    ToStringTesting<M> {

    BasicJsonMarshallerTestCase2() {
        super();
    }

    @Test
    public final void testType() {
        final M marshaller = this.marshaller();
        this.checkEquals(
            this.marshallerType(),
            marshaller.type(),
            () -> ".type failed for " + marshaller
        );
    }

    abstract Class<T> marshallerType();

    @Test
    public final void testUnmarshallNullFails() {
        assertThrows(
            java.lang.NullPointerException.class,
            () -> this.marshaller()
                .unmarshall(
                    null,
                    JSON_NODE_UNMARSHALL_CONTEXT
                )
        );
    }

    // not final
    @Test
    public void testUnmarshallJsonNullNode() {
        this.unmarshallAndCheck(
            JsonNode.nullNode(),
            this.jsonNullNode()
        );
    }

    abstract T jsonNullNode();

    @Test
    public final void testUnmarshall() {
        if (false == this instanceof BasicJsonMarshallerTypedCollectionCollectionTest) {
            this.unmarshallAndCheck(
                this.node(),
                this.value()
            );
        }
    }

    @Test
    public final void testUnmarshallWithType() {
        if (false == this instanceof BasicJsonMarshallerTypedCollectionCollectionTest) {
            this.unmarshallWithTypeAndCheck(
                this.nodeWithType(),
                this.value()
            );
        }
    }

    @Test
    public final void testMarshallWithNull() {
        this.marshallAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public final void testMarshall() {
        this.marshallAndCheck(
            this.value(),
            this.node()
        );
    }

    @Test
    public final void testMarshallWithTypeNull() {
        this.marshallWithTypeAndCheck(
            null,
            JsonNode.nullNode()
        );
    }

    @Test
    public final void testMarshallWithType() {
        this.marshallWithTypeAndCheck(
            this.value(),
            this.nodeWithType()
        );
    }

    @Test
    public final void testRoundtripMarshallWithTypeFromJsonNodeWithType() {
        if (false == this instanceof BasicJsonMarshallerTypedCollectionCollectionTest) {
            final T value = this.value();

            final JsonNode json = this.marshaller()
                .marshallWithType(
                    value,
                    JSON_NODE_MARSHALL_CONTEXT
                );

            this.checkEquals(value,
                JSON_NODE_UNMARSHALL_CONTEXT
                    .unmarshallWithType(json),
                () -> "roundtrip starting with value failed fromValue: " + value + " -> json: " + json
            );
        }
    }

    @Test
    public final void testRoundtripFromJsonNodeWithTypeMapperMarshallWithType() {
        if (false == this instanceof BasicJsonMarshallerTypedCollectionCollectionTest) {
            final JsonNode json = this.nodeWithType();

            final T value = JSON_NODE_UNMARSHALL_CONTEXT.
                unmarshallWithType(json);

            this.checkEquals(json,
                this.marshaller()
                    .marshallWithType(
                        value,
                        JSON_NODE_MARSHALL_CONTEXT
                    ),
                () -> "roundtrip starting with node failed, json: " + json + " -> value:: " + value
            );
        }
    }

    @Test
    public final void testRoundtripMarshallWithTypeObjectFromJsonNodeWithType() {
        if (false == this instanceof BasicJsonMarshallerTypedCollectionCollectionTest) {
            final T value = this.value();

            final JsonNode json = this.marshaller()
                .marshallWithType(
                    value,
                    JSON_NODE_MARSHALL_CONTEXT
                );

            this.checkEquals(value,
                JSON_NODE_UNMARSHALL_CONTEXT
                    .unmarshallWithType(json),
                () -> "roundtrip starting with value failed, value: " + value + " -> json: " + json
            );
        }
    }

    @Test
    public final void testRoundtripOptional() {
        final T value = this.value();
        final Optional<T> optional = Optional.of(value);

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallOptional(optional);

        this.checkEquals(
            optional,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallOptional(
                    jsonNode,
                    type(value)
                ),
            () -> "roundtrip optional: " + optional + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testRoundtripOptionalWithType() {
        final T value = this.value();
        final Optional<T> optional = Optional.of(value);

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallOptionalWithType(optional);

        this.checkEquals(
            optional,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallOptionalWithType(jsonNode),
            () -> "roundtrip optional: " + optional + " -> json: " + jsonNode
        );
    }

    // not final
    @Test
    public void testRoundtripList() {
        final T value = this.value();
        final List<T> list = List.of(value);

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallCollection(list);

        this.checkEquals(
            list,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallList(
                    jsonNode,
                    type(value)
                ),
            () -> "roundtrip list: " + list + " -> json: " + jsonNode
        );
    }

    // not final
    @Test
    public void testRoundtripSet() {
        final T value = this.value();
        final Set<T> set = Set.of(value);

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallCollection(set);

        this.checkEquals(
            set,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallSet(
                    jsonNode,
                    type(value)
                ),
            () -> "roundtrip set: " + set + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testRoundtripMapStringKey() {
        final T value = this.value();

        final Map<String, T> map = Map.of(
            "key1",
            value
        );

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallMap(map);

        this.checkEquals(
            map,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallMap(
                    jsonNode,
                    String.class,
                    type(value)
                ),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testRoundtripMapNonStringKey() {
        final T value = this.value();

        final Map<Integer, T> map = Map.of(123, value);

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT
            .marshallMap(map);

        this.checkEquals(
            map,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallMap(
                    jsonNode,
                    Integer.class,
                    type(value)
                ),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode
        );
    }

    private static Class<?> type(final Object value) {
        return value instanceof CurrencyCodeSet ||
            value instanceof CsvStringList ||
            value instanceof CsvStringSet ||
            value instanceof EnvironmentValueNameSet ||
            value instanceof ETagList ||
            value instanceof LocaleLanguageTagSet ||
            value instanceof LocalDateList ||
            value instanceof LocalDateTimeList ||
            value instanceof LocalTimeList ||
            value instanceof NumberList ||
            value instanceof StringList ||
            value instanceof TsvStringList ||
            value instanceof TsvStringSet ?
            value.getClass() :
            value instanceof List ?
                List.class :
                value instanceof Set ?
                    Set.class : value instanceof Map ?
                    Map.class :
                    value.getClass();
    }

    @Test
    public final void testRoundtripTypeList() {
        final List<T> list = List.of(this.value());

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT.marshallCollectionWithType(list);

        this.checkEquals(
            list,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallListWithType(jsonNode),
            () -> "roundtrip list: " + list + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testRoundtripTypeSet() {
        final Set<T> set = Set.of(this.value());

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT.marshallCollectionWithType(set);

        this.checkEquals(
            set,
            JSON_NODE_UNMARSHALL_CONTEXT.unmarshallSetWithType(jsonNode),
            () -> "roundtrip set: " + set + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testRoundtripTypeMap() {
        final Map<String, T> map = Map.of("key1", this.value());

        final JsonNode jsonNode = JSON_NODE_MARSHALL_CONTEXT.marshallMapWithType(map);

        this.checkEquals(
            map,
            JSON_NODE_UNMARSHALL_CONTEXT.unmarshallMapWithType(jsonNode),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode
        );
    }

    @Test
    public final void testToString() {
        this.toStringAndCheck(
            this.marshaller(),
            this.typeName()
        );
    }

    abstract M marshaller();

    abstract T value();

    abstract boolean requiresTypeName();

    abstract JsonNode node();

    final JsonNode nodeWithType() {
        final JsonNode node = this.node();
        return this.requiresTypeName() ?
            typeAndValue(node) :
            node;
    }

    final <T extends Throwable> void unmarshallFailed(final JsonNode node,
                                                      final Class<T> thrown) {
        Class<? extends Throwable> reallyThrown = JsonNodeUnmarshallException.class;
        if (JsonNodeException.class.isAssignableFrom(thrown) || java.lang.NullPointerException.class == thrown) {
            reallyThrown = thrown;
        }

        assertThrows(
            reallyThrown,
            () -> this.marshaller()
                .unmarshall(
                    node,
                    JSON_NODE_UNMARSHALL_CONTEXT
                )
        );
    }

    final void unmarshallAndCheck(final JsonNode node, final T value) {
        this.unmarshallAndCheck(this.marshaller(), node, value);
    }

    final void unmarshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                  final JsonNode node,
                                  final T value) {
        this.unmarshallAndCheck(
            marshaller,
            node,
            JSON_NODE_UNMARSHALL_CONTEXT,
            value
        );
    }

    final void unmarshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                  final JsonNode node,
                                  final JsonNodeUnmarshallContext context,
                                  final T value) {
        this.checkEquals(
            value,
            marshaller.unmarshall(
                node,
                context
            ),
            () -> "unmarshall failed " + node);
    }

    final void unmarshallWithTypeAndCheck(final JsonNode node,
                                          final T value) {
        this.checkEquals(
            value,
            JSON_NODE_UNMARSHALL_CONTEXT
                .unmarshallWithType(node),
            () -> "unmarshall failed " + node
        );
    }

    final void marshallAndCheck(final T value,
                                final JsonNode node) {
        this.marshallAndCheck(this.marshaller(), value, node);
    }

    final void marshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                final T value,
                                final JsonNode node) {
        this.checkEquals(
            node,
            marshaller.marshall(
                value,
                JSON_NODE_MARSHALL_CONTEXT
            ),
            () -> "marshall failed " + node
        );
    }

    final void marshallWithTypeAndCheck(final T value,
                                        final JsonNode node) {
        this.marshallWithTypeAndCheck(
            this.marshaller(),
            value,
            node
        );
    }

    final void marshallWithTypeAndCheck(final BasicJsonMarshaller<T> marshaller,
                                        final T value,
                                        final JsonNode node) {
        this.checkEquals(
            node,
            marshaller.marshallWithType(
                value,
                JSON_NODE_MARSHALL_CONTEXT
            ),
            () -> "marshallWithType failed " + node
        );
    }

    abstract String typeName();

    final JsonObject typeAndValue(final JsonNode value) {
        return typeAndValue(
            this.typeName(),
            value
        );
    }
}
