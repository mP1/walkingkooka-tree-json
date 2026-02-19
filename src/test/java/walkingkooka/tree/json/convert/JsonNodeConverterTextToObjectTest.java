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
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

public final class JsonNodeConverterTextToObjectTest extends JsonNodeConverterTestCase<JsonNodeConverterTextToObject<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext> {

    @Test
    public void testConvertStringToUnsupportedClassFails() {
        this.convertFails(
            "Unknown",
            Void.class
        );
    }

    @Test
    public void testConvertNullToBooleanFails() {
        this.convertFails(
            null,
            Boolean.class
        );
    }

    @Test
    public void testConvertJsonNullToBooleanFails() {
        this.convertFails(
            JsonNode.nullNode()
                .toBoolean(),
            Boolean.class
        );
    }

    @Test
    public void testConvertNullToNumberFails() {
        this.convertFails(
            null,
            Number.class
        );
    }

    @Test
    public void testConvertJsonNumberToNumberFails() {
        this.convertFails(
            JsonNode.number(123),
            Number.class
        );
    }
    
    @Test
    public void testConvertNullToStringFails() {
        this.convertFails(
            null,
            String.class
        );
    }

    @Test
    public void testConvertJsonNullToStringFails() {
        this.convertFails(
            JsonNode.nullNode()
                .toString(),
            String.class
        );
    }

    @Test
    public void testConvertStringToBigDecimalClassFails() {
        this.convertFails(
            JsonNode.string("1")
                .toString(),
            BigDecimal.class
        );
    }

    @Test
    public void testConvertStringToStringClassFails() {
        this.convertFails(
            JsonNode.string("Hello123")
                .toString(),
            String.class
        );
    }

    @Test
    public void testConvertJsonStringWithExpressionToString() {
        final Expression expression = Expression.add(
            Expression.value(1),
            Expression.value(23)
        );

        final FakeJsonNodeConverterContext context = this.createContext();

        this.convertAndCheck(
            this.createConverter(),
            JsonNodeMarshallContexts.basic()
                .marshall(expression)
                .toString(),
            expression.getClass(),
            context,
            Cast.to(expression)
        );
    }

    @Override
    public JsonNodeConverterTextToObject<FakeJsonNodeConverterContext> createConverter() {
        return JsonNodeConverterTextToObject.instance();
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

            @Override
            public <T> T unmarshall(final JsonNode json,
                                    final Class<T> type) {
                return this.context.unmarshall(json, type);
            }

            @Override
            public Optional<JsonString> typeName(final Class<?> type) {
                return this.context.typeName(type);
            }

            private final JsonNodeUnmarshallContext context = JsonNodeUnmarshallContexts.basic(
                (String cc) -> {
                    throw new UnsupportedOperationException();
                },
                (String lt) -> {
                    throw new UnsupportedOperationException();
                },
                ExpressionNumberKind.BIG_DECIMAL,
                MathContext.DECIMAL32
            );
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            JsonNodeConverterTextToObject.instance(),
            JsonNodeConverterTextToObject.class.getSimpleName()
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterTextToObject<FakeJsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeConverterTextToObject.class);
    }
}
