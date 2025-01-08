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

import walkingkooka.Cast;
import walkingkooka.collect.list.ImmutableList;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;

import java.math.MathContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class BasicJsonNodeUnmarshallContext extends BasicJsonNodeContext implements JsonNodeUnmarshallContext {

    /**
     * Factory
     */
    static BasicJsonNodeUnmarshallContext with(final ExpressionNumberKind kind,
                                               final MathContext mathContext) {
        Objects.requireNonNull(kind, "kind");
        Objects.requireNonNull(mathContext, "mathContext");

        return new BasicJsonNodeUnmarshallContext(
            kind,
            mathContext,
            JsonNodeUnmarshallContext.PRE_PROCESSOR
        );
    }

    /**
     * Private ctor
     */
    private BasicJsonNodeUnmarshallContext(final ExpressionNumberKind kind,
                                           final MathContext mathContext,
                                           final JsonNodeUnmarshallContextPreProcessor processor) {
        super();
        this.kind = kind;
        this.mathContext = mathContext;
        this.processor = processor;
    }

    @Override
    public ExpressionNumberKind expressionNumberKind() {
        return this.kind;
    }

    private final ExpressionNumberKind kind;

    @Override
    public MathContext mathContext() {
        return this.mathContext;
    }

    private final MathContext mathContext;

    @Override
    public JsonNodeUnmarshallContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
        Objects.requireNonNull(processor, "processor");

        return this.processor.equals(processor) ?
            this :
            new BasicJsonNodeUnmarshallContext(
                this.kind,
                this.mathContext,
                processor
            );
    }

    // from.............................................................................................................

    /**
     * Attempts to convert this node to the requested {@link Class type}.
     */
    @Override
    public <T> T unmarshall(final JsonNode node,
                            final Class<T> type) {
        return Cast.to(BasicJsonMarshaller.marshaller(type).unmarshall(this.preProcess(node, type), this));
    }

    @Override
    public <T extends Enum<T>> Set<T> unmarshallEnumSet(final JsonNode node,
                                                        final Class<T> enumClass,
                                                        final Function<String, T> stringToEnum) {
        return node.isNull() ?
            null :
            this.unmarshallEnumSetNonNull(
                node.stringOrFail(),
                enumClass,
                stringToEnum
            );
    }

    private <T extends Enum<T>> Set<T> unmarshallEnumSetNonNull(final String csv,
                                                                final Class<T> enumClass,
                                                                final Function<String, T> stringToEnum) {
        return csv.isEmpty() ?
            EnumSet.noneOf(enumClass) :
            Arrays.stream(csv.split(","))
                .map(stringToEnum::apply)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(enumClass)));
    }

    /**
     * Assumes this json object is an array holding elements that will be converted to the requested element type, returning
     * a {@link List} of them.
     */
    @Override
    public <T> List<T> unmarshallList(final JsonNode node,
                                      final Class<T> elementType) {
        return this.unmarshallCollection(
            node,
            elementType,
            ImmutableList.collector()
        );
    }

    /**
     * Assumes this json object is an array holding elements that will be converted to the requested element type, returning
     * a {@link Set} of them.
     */
    @Override
    public <T> Set<T> unmarshallSet(final JsonNode node,
                                    final Class<T> elementType) {
        return this.unmarshallCollection(node,
            elementType,
            Collectors.toCollection(Sets::ordered));
    }

    private <C extends Collection<T>, T> C unmarshallCollection(final JsonNode from,
                                                                final Class<T> elementType,
                                                                final Collector<T, ?, C> collector) {
        final BasicJsonMarshaller<T> marshaller = BasicJsonMarshaller.marshaller(elementType);
        return from.children()
            .stream()
            .map(c -> marshaller.unmarshall(this.preProcess(c, elementType), this))
            .collect(collector);
    }

    /**
     * If the node is a {@link JsonObject} unmarshalls the keys and values  otherwise assumes an array and unmarshalls
     * the entries made up of key and value properties.
     */
    @Override
    public <K, V> Map<K, V> unmarshallMap(final JsonNode node,
                                          final Class<K> keyType,
                                          final Class<V> valueType) {
        return node.isObject() ?
            unmarshallMapFromObject(node.objectOrFail(), keyType, valueType) :
            unmarshallMapFromArray(node, keyType, valueType);
    }

    private <K, V> Map<K, V> unmarshallMapFromObject(final JsonObject node,
                                                     final Class<K> keyType,
                                                     final Class<V> valueType) {
        final BasicJsonMarshaller<K> keyMapper = BasicJsonMarshaller.marshaller(keyType);
        final BasicJsonMarshaller<V> valueMapper = BasicJsonMarshaller.marshaller(valueType);

        final Map<K, V> map = Maps.ordered();

        for (final JsonNode entry : node.children()) {
            map.put(
                keyMapper.unmarshall(
                    this.preProcess(entry.name().toJsonString(), keyType),
                    this
                ),
                valueMapper.unmarshall(
                    this.preProcess(entry, valueType),
                    this
                )
            );
        }
        return map;
    }

    private <K, V> Map<K, V> unmarshallMapFromArray(final JsonNode node,
                                                    final Class<K> keyType,
                                                    final Class<V> valueType) {
        fromArrayCheck(node, Map.class);

        final BasicJsonMarshaller<K> keyMapper = BasicJsonMarshaller.marshaller(keyType);
        final BasicJsonMarshaller<V> valueMapper = BasicJsonMarshaller.marshaller(valueType);

        final Map<K, V> map = Maps.ordered();

        for (final JsonNode entry : node.children()) {
            final JsonObject entryObject = entry.objectOrFail();

            map.put(
                keyMapper.unmarshall(
                    this.preProcess(entryObject.getOrFail(BasicJsonMarshallerTypedMap.ENTRY_KEY), keyType),
                    this
                ),
                valueMapper.unmarshall(
                    this.preProcess(entryObject.getOrFail(BasicJsonMarshallerTypedMap.ENTRY_VALUE), valueType),
                    this
                )
            );
        }
        return map;
    }

    // unmarshallWithType.............................................................................................

    /**
     * Assumes a wrapper object with the type and value, basically the inverse of {@link JsonNodeMarshallContext#marshallWithType(Object)}.
     */
    @Override
    public <T> T unmarshallWithType(final JsonNode node) {
        return BasicJsonNodeUnmarshallContextJsonNodeVisitor.value(node, this);
    }

    /**
     * Assumes a {@link JsonArray} holding objects tagged with type and values.
     */
    @Override
    public <T> List<T> unmarshallWithTypeList(final JsonNode node) {
        return this.unmarshallCollectionWithType(
            node,
            List.class,
            ImmutableList.collector()
        );
    }

    /**
     * Assumes a {@link JsonArray} holding objects tagged with type and values.
     */
    @Override
    public <T> Set<T> unmarshallWithTypeSet(final JsonNode node) {
        return this.unmarshallCollectionWithType(node,
            Set.class,
            Collectors.toCollection(Sets::ordered));
    }

    private <C extends Collection<T>, T> C unmarshallCollectionWithType(final JsonNode from,
                                                                        final Class<?> label,
                                                                        final Collector<T, ?, C> collector) {
        return this.unmarshallCollection0(from,
            label,
            this::unmarshallWithType,
            collector);
    }

    /**
     * Assumes a {@link JsonArray} holding entries of the {@link Map} tagged with type and values.
     */
    @Override
    public <K, V> Map<K, V> unmarshallWithTypeMap(final JsonNode node) {
        fromArrayCheck(node, Map.class);

        final Map<K, V> map = Maps.ordered();

        for (JsonNode child : node.children()) {
            final JsonObject childObject = child.objectOrFail();

            map.put(this.unmarshallWithType(childObject.getOrFail(BasicJsonMarshallerTypedMap.ENTRY_KEY)),
                this.unmarshallWithType(childObject.getOrFail(BasicJsonMarshallerTypedMap.ENTRY_VALUE)));
        }

        return map;
    }

    // from helpers......................................................................................................

    private static void fromArrayCheck(final JsonNode node,
                                       final Class<?> label) {
        if (null != node && !node.isArray()) {
            throw new JsonNodeUnmarshallException("Required array for " + label.getSimpleName(), node);
        }
    }

    /**
     * Turns all the children nodes into a {@link Collection}.
     */
    private <C extends Collection<T>, T> C unmarshallCollection0(final JsonNode from,
                                                                 final Class<?> label,
                                                                 final Function<JsonNode, T> element,
                                                                 final Collector<T, ?, C> collector) {
        fromArrayCheck(from, label);

        return from.children()
            .stream()
            .map(element)
            .collect(collector);
    }

    /**
     * Pre process each {@link JsonNode} when requested to unmarshall.
     */
    private JsonNode preProcess(final JsonNode node,
                                final Class<?> type) {
        return this.processor.apply(node, type);
    }

    private final JsonNodeUnmarshallContextPreProcessor processor;
}
