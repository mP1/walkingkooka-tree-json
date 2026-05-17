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

package walkingkooka.tree.json.marshall.util;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.currency.CurrencyLocaleContexts;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;
import walkingkooka.util.FunctionTesting;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JsonNodeMarshallUnmarshallMapperFunctionTest implements FunctionTesting<JsonNodeMarshallUnmarshallMapperFunction<BigDecimal, String>, JsonNode, JsonNode>,
    ClassTesting<JsonNodeMarshallUnmarshallMapperFunction<BigDecimal, String>>,
    ToStringTesting<JsonNodeMarshallUnmarshallMapperFunction<BigDecimal, String>> {

    private final static JsonNodeUnmarshallContext UNMARSHALL_CONTEXT = JsonNodeUnmarshallContexts.basic(
        ExpressionNumberKind.BIG_DECIMAL,
        CurrencyLocaleContexts.fake(), // CurrencyCodeLanguageTagContext
        MathContext.DECIMAL32
    );

    private final static JsonNodeMarshallContext MARSHALL_CONTEXT = JsonNodeMarshallContexts.basic();

    private final static Function<BigDecimal, String> MAPPER = (b) -> b.toPlainString();

// with.............................................................................................................

    @Test
    public void testWithNullTypeFails() {
        withFails(
            null,
            UNMARSHALL_CONTEXT,
            MARSHALL_CONTEXT,
            MAPPER
        );
    }

    @Test
    public void testWithNullUnmarshallContextFails() {
        withFails(
            BigDecimal.class,
            null,
            MARSHALL_CONTEXT,
            MAPPER
        );
    }

    @Test
    public void testWithNullMarshallContextFails() {
        withFails(
            BigDecimal.class,
            UNMARSHALL_CONTEXT,
            null,
            MAPPER
        );
    }

    @Test
    public void testWithNullMapperFails() {
        withFails(
            BigDecimal.class,
            UNMARSHALL_CONTEXT,
            MARSHALL_CONTEXT,
            null
        );
    }

    private void withFails(final Class<BigDecimal> type,
                           final JsonNodeUnmarshallContext unmarshallContext,
                           final JsonNodeMarshallContext marshallContext,
                           final Function<BigDecimal, String> mapper) {

        assertThrows(NullPointerException.class, () -> JsonNodeMarshallUnmarshallMapperFunction.with(
            type,
            unmarshallContext,
            marshallContext,
            mapper
        ));
    }

    @Test
    public void testApply() {
        this.applyAndCheck(
            MARSHALL_CONTEXT.marshall(BigDecimal.valueOf(10.5)),
            MARSHALL_CONTEXT.marshall("10.5")
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createFunction(), MAPPER.toString());
    }

    @Override
    public JsonNodeMarshallUnmarshallMapperFunction<BigDecimal, String> createFunction() {
        return JsonNodeMarshallUnmarshallMapperFunction.with(
            BigDecimal.class,
            UNMARSHALL_CONTEXT,
            MARSHALL_CONTEXT,
            MAPPER
        );
    }

    @Override
    public Class<JsonNodeMarshallUnmarshallMapperFunction<BigDecimal, String>> type() {
        return Cast.to(JsonNodeMarshallUnmarshallMapperFunction.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
