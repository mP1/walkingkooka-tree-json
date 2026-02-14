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

import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;

import java.math.MathContext;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Delegate all methods for {@link JsonNodeUnmarshallContext} except for {@link #setPreProcessor(JsonNodeUnmarshallContextPreProcessor)},
 * forcing implementations to implement.
 */
public interface JsonNodeUnmarshallContextDelegator extends JsonNodeUnmarshallContext,
    JsonNodeContextDelegator {

    @Override
    default <T> T unmarshall(final JsonNode node,
                             final Class<T> type) {
        return this.jsonNodeUnmarshallContext()
            .unmarshall(
                node,
                type
            );
    }

    @Override
    default <T extends Enum<T>> Set<T> unmarshallEnumSet(final JsonNode node,
                                                         final Class<T> enumClass,
                                                         final Function<String, T> stringToEnum) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallEnumSet(
                node,
                enumClass,
                stringToEnum
            );
    }

    @Override
    default  <T> Optional<T> unmarshallOptional(final JsonNode node,
                                                final Class<T> valueType) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallOptional(
                node,
                valueType
            );
    }

    @Override
    default  <T> Optional<T> unmarshallOptionalWithType(final JsonNode node) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallOptionalWithType(node);
    }

    @Override
    default <T> List<T> unmarshallList(final JsonNode node,
                                       final Class<T> elementType) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallList(
                node,
                elementType
            );
    }

    @Override
    default <T> Set<T> unmarshallSet(final JsonNode node,
                                     final Class<T> elementType) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallSet(
                node,
                elementType
            );
    }

    @Override
    default <K, V> Map<K, V> unmarshallMap(final JsonNode node,
                                           final Class<K> keyType,
                                           final Class<V> valueType) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallMap(
                node,
                keyType,
                valueType
            );
    }

    @Override
    default <T> T unmarshallWithType(final JsonNode node) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallWithType(node);
    }

    @Override
    default <T> List<T> unmarshallListWithType(final JsonNode node) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallListWithType(node);
    }

    @Override default <T> Set<T> unmarshallSetWithType(final JsonNode node) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallSetWithType(node);
    }

    @Override
    default <K, V> Map<K, V> unmarshallMapWithType(final JsonNode node) {
        return this.jsonNodeUnmarshallContext()
            .unmarshallMapWithType(node);
    }

    @Override
    default MathContext mathContext() {
        return this.jsonNodeUnmarshallContext()
            .mathContext();
    }

    @Override
    default Optional<Currency> currencyForCurrencyCode(final String currencyCode) {
        return this.jsonNodeUnmarshallContext()
            .currencyForCurrencyCode(currencyCode);
    }

    @Override
    default ExpressionNumberKind expressionNumberKind() {
        return this.jsonNodeUnmarshallContext()
            .expressionNumberKind();
    }


    @Override
    default JsonNodeContext jsonNodeContext() {
        return this.jsonNodeUnmarshallContext();
    }

    JsonNodeUnmarshallContext jsonNodeUnmarshallContext();
}
