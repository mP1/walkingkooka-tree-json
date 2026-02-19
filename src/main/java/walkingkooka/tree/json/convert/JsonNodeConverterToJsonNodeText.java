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

package walkingkooka.tree.json.convert;

import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ShortCircuitingConverter;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link Converter} that supports converting a given value to a {@link JsonNode} and then converting that to text.
 */
final class JsonNodeConverterToJsonNodeText<C extends JsonNodeConverterContext> extends JsonNodeConverter<C>
    implements ShortCircuitingConverter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> JsonNodeConverterToJsonNodeText<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static JsonNodeConverterToJsonNodeText<?> INSTANCE = new JsonNodeConverterToJsonNodeText<>();

    private JsonNodeConverterToJsonNodeText() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return null != value &&
            context.isSupportedJsonType(value.getClass()) &&
            (String.class == type || CharSequence.class == type) &&
            context.canConvert(
                "",
                type
            );
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        return this.successfulConversion(
            context.convertOrFail(
                context.marshall(value)
                    .toJsonText(
                        context.indentation(),
                        context.lineEnding()
                    ),
                type
            ),
            type
        );
    }

    @Override
    public String toString() {
        return "* to JSON text";
    }
}
