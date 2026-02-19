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
import walkingkooka.convert.Converter;
import walkingkooka.convert.TextToTryingShortCircuitingConverter;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link Converter} that supports unmarshalling text holding json to a requested {@link Class}.
 */
final class JsonNodeConverterTextToObject<C extends JsonNodeConverterContext> extends JsonNodeConverter<C>
    implements TextToTryingShortCircuitingConverter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> JsonNodeConverterTextToObject<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static JsonNodeConverterTextToObject<?> INSTANCE = new JsonNodeConverterTextToObject<>();

    private JsonNodeConverterTextToObject() {
        super();
    }

    @Override
    public boolean isTargetType(final Object value,
                                final Class<?> type,
                                final C context) {
        // list of exceptions to try and avoid StackOverflowError
        return false == value instanceof JsonNode &&
            false == (
                type == Boolean.class ||
                    ExpressionNumber.isClass(type) ||
                    Number.class == type ||
                    type == String.class
            ) &&
            context.isSupportedJsonType(type);
    }

    /**
     * Unmarshalls the json text.
     */
    @Override
    public Object parseText(final String text,
                            final Class<?> type,
                            final C context) {
        return null == text ?
            null :
            context.unmarshall(
                JsonNode.parse(text),
                type
            );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
