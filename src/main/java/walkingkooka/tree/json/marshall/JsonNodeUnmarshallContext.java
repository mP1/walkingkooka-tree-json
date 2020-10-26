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
import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.ExpressionNumberContext;
import walkingkooka.tree.expression.HasExpressionNumberKind;
import walkingkooka.tree.json.JsonArray;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;
import walkingkooka.tree.json.JsonPropertyName;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A {@link Context} that accompanies transforming {@link JsonNode} into an object.
 */
public interface JsonNodeUnmarshallContext extends JsonNodeContext,
        ExpressionNumberContext {

    /**
     * A {@link BiFunction processor} that simply returns the given object ignoring the value.
     */
    BiFunction<JsonObject, Class<?>, JsonObject> OBJECT_PRE_PROCESSOR = (jsonObject, type) -> jsonObject;

    /**
     * Shared function used to report a required property is missing within a static unmarshall.
     */
    static void requiredPropertyMissing(final JsonPropertyName property,
                                        final JsonNode node) {
        throw new JsonNodeUnmarshallException("Required property " + CharSequences.quoteAndEscape(property.value()) + " missing=" + node,
                node);
    }

    /**
     * Shared function used to report a required property is missing within a static unmarshall.
     */
    static void unknownPropertyPresent(final JsonPropertyName property,
                                       final JsonNode node) {
        throw new JsonNodeUnmarshallException("Unknown property " + CharSequences.quoteAndEscape(property.value()) + " in " + node,
                node);
    }

    /**
     * Sets or replaces the {@link BiFunction object pre processor} creating a new instance as necessary.
     */
    JsonNodeUnmarshallContext setObjectPreProcessor(final BiFunction<JsonObject, Class<?>, JsonObject> processor);

    // from.............................................................................................................

    /**
     * Attempts to convert this node to the requested {@link Class type}.
     */
    <T> T unmarshall(final JsonNode node,
                     final Class<T> type);

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
    <T> List<T> unmarshallWithTypeList(final JsonNode node);

    /**
     * Assumes a {@link JsonArray} holding objects tagged with type and values.
     */
    <T> Set<T> unmarshallWithTypeSet(final JsonNode node);

    /**
     * Assumes a {@link JsonArray} holding entries of the {@link Map} tagged with type and values.
     */
    <K, V> Map<K, V> unmarshallWithTypeMap(final JsonNode node);

    /**
     * {@see JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction}
     */
    default <T> BiFunction<JsonNode, JsonNodeUnmarshallContext, T> unmarshallWithType(final JsonPropertyName property,
                                                                                      final JsonObject propertySource,
                                                                                      final Class<T> superType) {
        return JsonNodeUnmarshallContextUnmarshallWithTypePropertyBiFunction.with(property, propertySource, superType);
    }
}
