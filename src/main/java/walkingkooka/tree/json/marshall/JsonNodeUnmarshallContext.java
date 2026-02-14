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

import walkingkooka.Context;
import walkingkooka.math.HasMathContext;
import walkingkooka.tree.expression.HasExpressionNumberKind;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.MissingPropertyJsonNodeException;
import walkingkooka.tree.json.UnknownPropertyJsonNodeException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link Context} that accompanies transforming {@link JsonNode} into an object.
 */
public interface JsonNodeUnmarshallContext extends JsonNodeContext,
    HasExpressionNumberKind,
    HasMathContext {

    /**
     * A {@link JsonNodeUnmarshallContextPreProcessor processor} that simply returns the given {@link JsonNode} ignoring the type.
     */
    JsonNodeUnmarshallContextPreProcessor PRE_PROCESSOR = (node, type) -> node;

    /**
     * Shared namedFunction used to report a required property is missing within a static unmarshall.
     */
    static void missingProperty(final JsonPropertyName property,
                                final JsonNode node) {
        throw new MissingPropertyJsonNodeException(property, node);
    }

    /**
     * Shared namedFunction used to report a required property is missing within a static unmarshall.
     */
    static void unknownPropertyPresent(final JsonPropertyName property,
                                       final JsonNode node) {
        throw new UnknownPropertyJsonNodeException(property, node);
    }

    /**
     * Sets or replaces the {@link BiFunction json node pre processor} creating a new instance as necessary.
     */
    JsonNodeUnmarshallContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor);

    // from.............................................................................................................

    /**
     * Attempts to convert this node to the requested {@link Class type}.
     */
    <T> T unmarshall(final JsonNode node,
                     final Class<T> type);

    /**
     * Unmarshalls the {@link JsonNode} to a {@link Set} using the provided {@link Enum} string factory.
     */
    <T extends Enum<T>> Set<T> unmarshallEnumSet(final JsonNode node,
                                                 final Class<T> enumClass,
                                                 final Function<String, T> stringToEnum);

    /**
     * Assumes a {@link JsonNode} into an {@link java.util.Optional}.
     */
    <T> Optional<T> unmarshallOptional(final JsonNode node,
                                       final Class<T> valueType);

    /**
     * Assumes a {@link JsonNode} into an {@link java.util.Optional}.
     */
    <T> Optional<T> unmarshallOptionalWithType(final JsonNode node);

    /**
     * Assumes something like a {@link JsonArray} and returns a {@link List} assuming the type of each element is fixed.
     */
    <T> List<T> unmarshallList(final JsonNode node,
                               final Class<T> elementType);

    /**
     * Assumes something like a {@link JsonArray} and returns a {@link Set} assuming the type of each element is fixed.
     */
    <T> Set<T> unmarshallSet(final JsonNode node,
                             final Class<T> elementType);

    /**
     * Assumes something like a {@link JsonArray} of entries and returns a {@link Map} using the key and value types.
     */
    <K, V> Map<K, V> unmarshallMap(final JsonNode node,
                                   final Class<K> keyType,
                                   final Class<V> valueType);

    // unmarshallWithType.............................................................................................

    /**
     * Assumes a wrapper object with the type and value, basically the inverse of {@link JsonNodeMarshallContext#marshallWithType(Object)}.
     */
    <T> T unmarshallWithType(final JsonNode node);

    /**
     * Assumes a {@link JsonArray} holding objects tagged with type and values.
     */
    <T> List<T> unmarshallListWithType(final JsonNode node);

    /**
     * Assumes a {@link JsonArray} holding objects tagged with type and values.
     */
    <T> Set<T> unmarshallSetWithType(final JsonNode node);

    /**
     * Assumes a {@link JsonArray} holding entries of the {@link Map} tagged with type and values.
     */
    <K, V> Map<K, V> unmarshallMapWithType(final JsonNode node);

    /**
     * {@see JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction}
     */
    default <T> BiFunction<JsonNode, JsonNodeUnmarshallContext, T> unmarshallWithType(final JsonPropertyName property,
                                                                                      final JsonObject propertySource,
                                                                                      final Class<T> superType) {
        return JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction.with(property, propertySource, superType);
    }
}
