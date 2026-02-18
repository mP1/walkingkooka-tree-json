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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * A fake {@link JsonNodeUnmarshallContext}
 */
public class FakeJsonNodeUnmarshallContext extends FakeJsonNodeContext implements JsonNodeUnmarshallContext {

    @Override
    public ExpressionNumberKind expressionNumberKind() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MathContext mathContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonNodeUnmarshallContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unmarshall(final JsonNode node,
                            final Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Enum<T>> Set<T> unmarshallEnumSet(final JsonNode node,
                                                        final Class<T> enumClass,
                                                        final Function<String, T> stringToEnum) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> unmarshallOptional(final JsonNode node,
                                              final Class<T> valueType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> unmarshallOptionalWithType(final JsonNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<T> unmarshallList(final JsonNode node,
                                      final Class<T> elementType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Set<T> unmarshallSet(final JsonNode node,
                                    final Class<T> elementType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> Map<K, V> unmarshallMap(final JsonNode node,
                                          final Class<K> keyType,
                                          final Class<V> valueType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unmarshallWithType(final JsonNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<T> unmarshallListWithType(final JsonNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Set<T> unmarshallSetWithType(final JsonNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> Map<K, V> unmarshallMapWithType(final JsonNode node) {
        throw new UnsupportedOperationException();
    }

    // CanCurrencyForCurrencyCode.......................................................................................

    @Override
    public Optional<Currency> currencyForCurrencyCode(final String currencyCode) {
        throw new UnsupportedOperationException();
    }

    // CanLocaleForLanguageTag..........................................................................................

    @Override
    public Optional<Locale> localeForLanguageTag(final String languageTag) {
        throw new UnsupportedOperationException();
    }
}
