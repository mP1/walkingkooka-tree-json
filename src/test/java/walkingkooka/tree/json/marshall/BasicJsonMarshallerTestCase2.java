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
import walkingkooka.collect.set.CsvStringSet;
import walkingkooka.datetime.LocalDateList;
import walkingkooka.datetime.LocalDateTimeList;
import walkingkooka.datetime.LocalTimeList;
import walkingkooka.environment.EnvironmentValueNameSet;
import walkingkooka.math.NumberList;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;
import walkingkooka.tree.json.JsonObject;

import java.math.MathContext;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BasicJsonMarshallerTestCase2<M extends BasicJsonMarshaller<T>, T> extends BasicJsonMarshallerTestCase<M>
    implements ToStringTesting<M> {

    BasicJsonMarshallerTestCase2() {
        super();
    }

    @Test
    public final void testType() {
        final M marshaller = this.marshaller();
        this.checkEquals(this.marshallerType(), marshaller.type(), () -> ".type failed for " + marshaller);
    }

    abstract Class<T> marshallerType();

    @Test
    public final void testUnmarshallNullFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.marshaller().unmarshall(null, this.unmarshallContext()));
    }

    @Test
    public void testUnmarshallJsonNullNode() {
        this.unmarshallAndCheck(JsonNode.nullNode(), this.jsonNullNode());
    }

    abstract T jsonNullNode();

    @Test
    public final void testUnmarshall() {
        this.unmarshallAndCheck(this.node(), this.value());
    }

    @Test
    public final void testUnmarshallWithType() {
        this.unmarshallWithTypeAndCheck(this.nodeWithType(), this.value());
    }

    @Test
    public final void testMarshallWithNull() {
        this.marshallAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public final void testMarshall() {
        this.marshallAndCheck(this.value(), this.node());
    }

    @Test
    public final void testMarshallWithTypeNull() {
        this.marshallWithTypeAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public final void testMarshallWithType() {
        this.marshallWithTypeAndCheck(this.value(), this.nodeWithType());
    }

    @Test
    public final void testRoundtripMarshallWithTypeFromJsonNodeWithType() {
        final T value = this.value();

        final JsonNode json = this.marshaller()
            .marshallWithType(value, this.marshallContext());

        this.checkEquals(value,
            this.unmarshallContext().unmarshallWithType(json),
            () -> "roundtrip starting with value failed fromValue: " + value + " -> json: " + json);
    }

    @Test
    public final void testRoundtripFromJsonNodeWithTypeMapperMarshallWithType() {
        final JsonNode json = this.nodeWithType();

        final T value = this.unmarshallContext().
            unmarshallWithType(json);

        this.checkEquals(json,
            this.marshaller().marshallWithType(value, this.marshallContext()),
            () -> "roundtrip starting with node failed, json: " + json + " -> value:: " + value);
    }

    @Test
    public final void testRoundtripMarshallWithTypeObjectFromJsonNodeWithType() {
        final T value = this.value();

        final JsonNode json = this.marshaller()
            .marshallWithType(value, this.marshallContext());

        this.checkEquals(value,
            this.unmarshallContext().unmarshallWithType(json),
            () -> "roundtrip starting with value failed, value: " + value + " -> json: " + json);
    }

    @Test
    public void testRoundtripOptional() {
        final T value = this.value();
        final Optional<T> optional = Optional.of(value);

        final JsonNode jsonNode = this.marshallContext()
            .marshallOptional(optional);

        this.checkEquals(
            optional,
            this.unmarshallContext()
                .unmarshallOptional(
                    jsonNode, 
                    type(value)
                ),
            () -> "roundtrip optional: " + optional + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripOptionalWithType() {
        final T value = this.value();
        final Optional<T> optional = Optional.of(value);

        final JsonNode jsonNode = this.marshallContext()
            .marshallOptionalWithType(optional);

        this.checkEquals(
            optional,
            this.unmarshallContext()
                .unmarshallOptionalWithType(jsonNode),
            () -> "roundtrip optional: " + optional + " -> json: " + jsonNode);
    }
    
    @Test
    public void testRoundtripList() {
        final T value = this.value();
        final List<T> list = List.of(value);

        final JsonNode jsonNode = this.marshallContext().marshallCollection(list);

        this.checkEquals(list,
            this.unmarshallContext().unmarshallList(jsonNode, type(value)),
            () -> "roundtrip list: " + list + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripSet() {
        final T value = this.value();
        final Set<T> set = Set.of(value);

        final JsonNode jsonNode = this.marshallContext().marshallCollection(set);

        this.checkEquals(
            set,
            this.unmarshallContext().unmarshallSet(jsonNode, type(value)),
            () -> "roundtrip set: " + set + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripMapStringKey() {
        final T value = this.value();

        final Map<String, T> map = Map.of("key1", value);

        final JsonNode jsonNode = this.marshallContext()
            .marshallMap(map);

        this.checkEquals(
            map,
            this.unmarshallContext().unmarshallMap(jsonNode, String.class, type(value)),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode
        );
    }

    @Test
    public void testRoundtripMapNonStringKey() {
        final T value = this.value();

        final Map<Integer, T> map = Map.of(123, value);

        final JsonNode jsonNode = this.marshallContext()
            .marshallMap(map);

        this.checkEquals(
            map,
            this.unmarshallContext()
                .unmarshallMap(jsonNode, Integer.class, type(value)),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode
        );
    }

    private static Class<?> type(final Object value) {
        return value instanceof CsvStringList ||
            value instanceof CsvStringSet ||
            value instanceof EnvironmentValueNameSet ||
            value instanceof LocalDateList ||
            value instanceof LocalDateTimeList ||
            value instanceof LocalTimeList ||
            value instanceof NumberList ||
            value instanceof StringList ?
            value.getClass() :
            value instanceof List ?
                List.class :
                value instanceof Set ?
                    Set.class : value instanceof Map ?
                    Map.class :
                    value.getClass();
    }

    @Test
    public void testRoundtripTypeList() {
        final List<T> list = List.of(this.value());

        final JsonNode jsonNode = this.marshallContext().marshallCollectionWithType(list);

        this.checkEquals(list,
            this.unmarshallContext().unmarshallListWithType(jsonNode),
            () -> "roundtrip list: " + list + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripTypeSet() {
        final Set<T> set = Set.of(this.value());

        final JsonNode jsonNode = this.marshallContext().marshallCollectionWithType(set);

        this.checkEquals(set,
            this.unmarshallContext().unmarshallSetWithType(jsonNode),
            () -> "roundtrip set: " + set + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripTypeMap() {
        final Map<String, T> map = Map.of("key1", this.value());

        final JsonNode jsonNode = this.marshallContext().marshallMapWithType(map);

        this.checkEquals(map,
            this.unmarshallContext().unmarshallMapWithType(jsonNode),
            () -> "roundtrip marshall: " + map + " -> json: " + jsonNode);
    }

    @Test
    public final void testToString() {
        this.toStringAndCheck(this.marshaller(), this.typeName());
    }

    abstract M marshaller();

    abstract T value();

    abstract boolean requiresTypeName();

    abstract JsonNode node();

    final JsonNode nodeWithType() {
        final JsonNode node = this.node();
        return this.requiresTypeName() ?
            this.typeAndValue(node) :
            node;
    }

    final <T extends Throwable> void unmarshallFailed(final JsonNode node,
                                                      final Class<T> thrown) {
        final JsonNodeUnmarshallContext context = this.unmarshallContext();

        Class<? extends Throwable> reallyThrown = JsonNodeUnmarshallException.class;
        if (JsonNodeException.class.isAssignableFrom(thrown) || java.lang.NullPointerException.class == thrown) {
            reallyThrown = thrown;
        }

        assertThrows(
            reallyThrown,
            () -> this.marshaller()
                .unmarshall(
                    node,
                    context
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
            this.unmarshallContext(),
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
        this.checkEquals(value,
            this.unmarshallContext().unmarshallWithType(node),
            () -> "unmarshall failed " + node);
    }

    final void marshallAndCheck(final T value,
                                final JsonNode node) {
        this.marshallAndCheck(this.marshaller(), value, node);
    }

    final void marshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                final T value,
                                final JsonNode node) {
        this.checkEquals(node,
            marshaller.marshall(value, this.marshallContext()),
            () -> "marshall failed " + node);
    }

    final void marshallWithTypeAndCheck(final T value,
                                        final JsonNode node) {
        this.marshallWithTypeAndCheck(this.marshaller(), value, node);
    }

    final void marshallWithTypeAndCheck(final BasicJsonMarshaller<T> marshaller,
                                        final T value,
                                        final JsonNode node) {
        this.checkEquals(node,
            marshaller.marshallWithType(value, this.marshallContext()),
            () -> "marshallWithType failed " + node);
    }

    abstract String typeName();

    final JsonObject typeAndValue(final JsonNode value) {
        return this.typeAndValue(this.typeName(), value);
    }

    final JsonNodeUnmarshallContext unmarshallContext() {
        return BasicJsonNodeUnmarshallContext.with(
            (String cc) -> Optional.ofNullable(
                Currency.getInstance(cc)
            ),
            (lt) -> Optional.of(
                Locale.forLanguageTag(lt)
            ),
            ExpressionNumberKind.DEFAULT,
            MathContext.DECIMAL32
        );
    }

    final JsonNodeMarshallContext marshallContext() {
        return BasicJsonNodeMarshallContext.INSTANCE;
    }
}
