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

import walkingkooka.convert.Converter;
import walkingkooka.tree.json.select.JsonSelector;

/**
 * A {@link Converter} that supports parsing {@link String text} into a {@link JsonSelector}.
 */
final class JsonNodeConverterTextToJsonSelector<C extends JsonNodeConverterContext> extends JsonNodeConverterTextTo<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> JsonNodeConverterTextToJsonSelector<C> instance() {
        return INSTANCE;
    }

    /**
     * Singleton
     */
    private final static JsonNodeConverterTextToJsonSelector INSTANCE = new JsonNodeConverterTextToJsonSelector<>();

    private JsonNodeConverterTextToJsonSelector() {
        super();
    }

    @Override
    public boolean isTargetType(final Object value,
                                final Class<?> type,
                                final C context) {
        return JsonSelector.class == type;
    }

    @Override
    public Object parseText(final String text,
                            final Class<?> type,
                            final C context) {
        return JsonSelector.parse(text);
    }

    @Override
    public String toString() {
        return TEXT + " to " + JsonSelector.class.getSimpleName();
    }
}
