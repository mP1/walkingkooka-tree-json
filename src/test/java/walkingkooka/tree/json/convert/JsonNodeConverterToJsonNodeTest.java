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
import walkingkooka.convert.Converters;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.FakeExpressionNumberConverterContext;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;

public final class JsonNodeConverterToJsonNodeTest extends JsonNodeConverterTestCase<JsonNodeConverterToJsonNode<JsonNodeConverterContext>, JsonNodeConverterContext> {

    @Test
    public void testConvertToStringFails() {
        this.convertFails(
            "{}",
            String.class
        );
    }

    @Test
    public void testConvertToJsonNode() {
        final JsonNodeConverterContext context = this.createContext();
        final JsonNodeConverterToJsonNode<JsonNodeConverterContext> converter = this.createConverter();

        final ExpressionNumber number = context.expressionNumberKind().create(123);

        this.convertAndCheck(
            converter,
            number,
            JsonNode.class,
            context,
            context.marshall(number)
        );
    }

    @Test
    public void testConvertStringToJsonNodeTo() {
        final JsonNodeConverterContext context = this.createContext();

        final ExpressionNumber number = context.expressionNumberKind()
            .create(123);

        this.convertAndCheck(
            JsonNodeConverters.toJsonNode()
                .to(
                    JsonNode.class, // required by JsonNodeConverterToJsonNode
                    Converters.objectToString()
                ),
            number,
            String.class,
            context,
            context.marshall(number).toString()
        );
    }

    @Override
    public JsonNodeConverterToJsonNode<JsonNodeConverterContext> createConverter() {
        return JsonNodeConverterToJsonNode.instance();
    }

    @Override
    public JsonNodeConverterContext createContext() {
        final ExpressionNumberKind kind = ExpressionNumberKind.BIG_DECIMAL;

        return JsonNodeConverterContexts.basic(
            new FakeExpressionNumberConverterContext() {

                @Override
                public ExpressionNumberKind expressionNumberKind() {
                    return kind;
                }
            },
            JsonNodeMarshallUnmarshallContexts.basic(
                JsonNodeMarshallContexts.basic(),
                JsonNodeUnmarshallContexts.basic(
                    (String cc) -> {
                        throw new UnsupportedOperationException();
                    },
                    (String lt) -> {
                        throw new UnsupportedOperationException();
                    },
                    kind,
                    MathContext.DECIMAL32
                )
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createConverter(),
            "* to JsonNode"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterToJsonNode<JsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeConverterToJsonNode.class);
    }
}
