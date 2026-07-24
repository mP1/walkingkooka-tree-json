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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.tree.json.pointer.JsonPointer;
import walkingkooka.tree.json.select.JsonSelector;

public final class JsonNodeConverterTextToJsonSelectorTest extends JsonNodeConverterTestCase<JsonNodeConverterTextToJsonSelector<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext> {

    @Test
    public void testConvertStringToUnsupportedClassFails() {
        this.convertFails(
            "Unknown",
            Void.class
        );
    }

    @Test
    public void testConvertNullToJsonPointerFails() {
        this.convertFails(
            null,
            JsonPointer.class
        );
    }

    @Test
    public void testConvertStringToJsonPointer() {
        final String selector = "/hello/world";/**/

        this.convertAndCheck(
            selector,
            JsonSelector.parse(selector)
        );
    }

    @Override
    public JsonNodeConverterTextToJsonSelector<FakeJsonNodeConverterContext> createConverter() {
        return JsonNodeConverterTextToJsonSelector.instance();
    }

    @Override
    public FakeJsonNodeConverterContext createContext() {
        return new FakeJsonNodeConverterContext() {

            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return this.converter.canConvert(
                    value,
                    type,
                    this
                );
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> type) {
                return this.converter.convert(
                    value,
                    type,
                    this
                );
            }

            private final Converter<FakeJsonNodeConverterContext> converter = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            JsonNodeConverterTextToJsonSelector.instance(),
            "TEXT to JsonSelector"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterTextToJsonSelector<FakeJsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeConverterTextToJsonSelector.class);
    }
}
